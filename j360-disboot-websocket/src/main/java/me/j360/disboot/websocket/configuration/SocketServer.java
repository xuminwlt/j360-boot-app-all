package me.j360.disboot.websocket.configuration;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.store.pubsub.PubSubListener;
import com.vip.vjtools.vjkit.net.NetUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.j360.disboot.websocket.constant.PubSubEventType;
import me.j360.disboot.websocket.constant.SocketEventType;
import me.j360.disboot.websocket.dispatcher.EventDispatcher;
import me.j360.disboot.websocket.message.Command;
import me.j360.disboot.websocket.message.request.BaseMessageRequest;
import org.redisson.api.RedissonClient;

import java.util.Objects;
import java.util.UUID;

/**
 * @author: min_xu
 */
@Slf4j
public class SocketServer {

    @Getter
    private SocketIOServer server;
    @Getter
    private RedissonPubSubEventStore eventStore;

    private final EventDispatcher eventDispatcher;
    private final RedissonClient redisson;


    public SocketServer(EventDispatcher eventDispatcher, RedissonClient redisson) {
        this.eventDispatcher = eventDispatcher;
        this.redisson = redisson;
    }

    public void init(int port) {
        RedissonStoreFactoryV2 redisStoreFactory = new RedissonStoreFactoryV2(redisson);
        this.eventStore = redisStoreFactory.eventStore();

        // Instantiate SocketIO Configuration
        Configuration config = new Configuration();
        config.setHostname(NetUtil.getHostName());
        config.setPort(port);
        config.setStoreFactory(redisStoreFactory);

        config.setAuthorizationListener((data) -> {
            //HandshakeData
            try {
                eventDispatcher.handshake(data);
                return true;
            } catch (Exception e) {
                return false;
            }
        });

        SocketConfig socketConfig = config.getSocketConfig();
        socketConfig.setReuseAddress(true);

        // Instantiate SocketIO Server
        this.server = new SocketIOServer(config);

        // 注册Session事件
        server.addDisconnectListener((client) -> {
            eventDispatcher.onDisconnect(client);
        });
        server.addConnectListener((client) -> {
            eventDispatcher.onconnect(client);
        });
        server.addPingListener((client) -> {
            eventDispatcher.onPing(client);
        });

        // 注册需要订阅客户端的事件
        for (SocketEventType eventType : SocketEventType.values()) {
            server.addEventListener(eventType.name(), BaseMessageRequest.class, new DataListener<BaseMessageRequest>() {
                @Override
                public void onData(SocketIOClient client, BaseMessageRequest data, AckRequest ackRequest) {
                    Command command = new Command();
                    command.setCmd(eventType.name());
                    command.setData(data);
                    eventDispatcher.onReceive(client, command, ackRequest);
                }
            });
        }

        // 注册需要返回给客户端的消息
        for (PubSubEventType eventType : PubSubEventType.values()) {
            eventStore.subscribe(eventType, new PubSubListener<DispatchEventMessage>() {
                @Override
                public void onMessage(DispatchEventMessage data) {
                    SocketIOClient client = server.getClient(UUID.fromString(data.getUuid()));
                    if (Objects.nonNull(client)) {
                        client.send(data.getPacket());
                    }
                }
            }, DispatchEventMessage.class);
        }
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop();
    }

}
