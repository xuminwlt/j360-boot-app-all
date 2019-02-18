package me.j360.disboot.websocket;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.listener.PingListener;
import com.corundumstudio.socketio.protocol.Packet;
import com.corundumstudio.socketio.protocol.PacketType;
import com.corundumstudio.socketio.store.pubsub.PubSubListener;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * @author: min_xu
 * @date: 2019/2/15 10:54 AM
 * 说明：
 */

@Slf4j
public class SocketServer {

    private SocketIOServer server;
    private RedissonClient redisson;
    private RedissonPubSubEventStore eventStore;

    public void start(int port) {
        Config redissonConfig = new Config();
        redissonConfig.useSingleServer().setAddress("redis://127.0.0.1:6379");
        redisson = Redisson.create(redissonConfig);
        RedissonStoreFactoryV2 redisStoreFactory = new RedissonStoreFactoryV2(redisson);
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(port);
        config.setStoreFactory(redisStoreFactory);

        eventStore = redisStoreFactory.eventStore();
        config.setAuthorizationListener(new AuthorizationListener() {
            @Override
            public boolean isAuthorized(HandshakeData data) {
                System.out.println("authorized: " + data.getSingleUrlParam("jwt"));
                return true;
            }
        });

        SocketConfig socketConfig = config.getSocketConfig();
        socketConfig.setReuseAddress(true);
        // Instantiate SocketIO Server
        this.server = new SocketIOServer(config);

        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {

            }
        });

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                //client.getHandshakeData().getSingleUrlParam();
                //client.getSessionId();
                client.joinRoom("room1");
                client.joinRoom(client.getSessionId().toString());

                RSet<String> rList = redisson.getSet("users");
                rList.add(client.getSessionId().toString());
            }
        });

        server.addPingListener(new PingListener() {
            @Override
            public void onPing(SocketIOClient client) {

            }
        });



        server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                //server.getBroadcastOperations().sendEvent("chatevent", data);

                //server.getRoomOperations("room1").sendEvent("chatevent", data);

                client.sendEvent("chatevent", data);

                System.out.println("current rooms:"+client.getAllRooms());
                ChatObject rData = new ChatObject();
                rData.setUserName(data.getUserName());
                rData.setMessage("hello");

                System.out.println(client.getAllRooms());
                //2个房间,一个发送给自己,一个发送给房间
//                for (String room : client.getAllRooms()) {
//                    server.getRoomOperations(room).sendEvent("chatevent", rData);
//                }

                RSet<String> rSet = redisson.getSet("users");
                for (String uuid : rSet) {
                    SocketIOClient other = server.getClient(UUID.fromString(uuid));
                    if (Objects.isNull(other)) {
                        System.out.println("发送失败: " + uuid);
                        sendAnyone(uuid, "chatevent", data);
                    } else  {
                        System.out.println("发送成功: " + uuid);
                        other.sendEvent("chatevent", rData);
                    }
                }
            }
        });


        //发送自定义的消息
        eventStore.subscribe(BizEventPubSubType.CHATEVENT, new PubSubListener<DispatchEventMessage>() {
            @Override
            public void onMessage(DispatchEventMessage data) {
                //订阅消费的推送给个人的消息
                SocketIOClient client = server.getClient(UUID.fromString(data.getUuid()));
                log.info("发送消息: {}", client);
                if (Objects.nonNull(client)) {

                    ChatObject rData = new ChatObject();
                    rData.setUserName("");
                    rData.setMessage("sixin");

                    //client.send(data.getPacket());
                    client.sendEvent("chatevent", rData);
                }
            }
        }, DispatchEventMessage.class);

        server.start();
    }


    public void sendAnyone(String uuid, String event, Object... data) {
        Packet packet = new Packet(PacketType.MESSAGE);
        packet.setSubType(PacketType.EVENT);
        packet.setName(event);
        packet.setData(Arrays.asList(data));

        DispatchEventMessage message = new DispatchEventMessage(uuid, packet);
        eventStore.publish(BizEventPubSubType.CHATEVENT, message);
    }


}
