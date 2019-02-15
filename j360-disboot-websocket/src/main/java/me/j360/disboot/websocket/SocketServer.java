package me.j360.disboot.websocket;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.listener.PingListener;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author: min_xu
 * @date: 2019/2/15 10:54 AM
 * 说明：
 */

@Component
public class SocketServer {

    @Autowired
    private BizService bizService;

    private SocketIOServer server;

    public void start() {
        // Instantiate Redisson configuration
        Config redissonConfig = new Config();
        redissonConfig.useSingleServer().setAddress("redis://127.0.0.1:6379");

        // Instantiate Redisson connection
        RedissonClient redisson = Redisson.create(redissonConfig);

        // Instantiate RedissonClientStoreFactory
        RedissonStoreFactory redisStoreFactory = new RedissonStoreFactory(redisson);

        // Instantiate SocketIO Configuration
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        config.setStoreFactory(redisStoreFactory);

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

            }
        });

        server.addPingListener(new PingListener() {
            @Override
            public void onPing(SocketIOClient client) {

            }
        });


        server.addEventListener("ackevent1", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(final SocketIOClient client, ChatObject data, final AckRequest ackRequest) {

                // check is ack requested by client,
                // but it's not required check
                if (ackRequest.isAckRequested()) {
                    // send ack response with data to client
                    ackRequest.sendAckData("client message was delivered to server!", "yeah!");
                }

                // send message back to client with ack callback WITH data
                ChatObject ackChatObjectData = new ChatObject(data.getUserName(), "message with ack data");
                client.sendEvent("ackevent2", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getSessionId() + " data: " + result);
                    }
                }, ackChatObjectData);

                ChatObject ackChatObjectData1 = new ChatObject(data.getUserName(), "message with void ack");
                client.sendEvent("ackevent3", new VoidAckCallback() {

                    protected void onSuccess() {
                        System.out.println("void ack from: " + client.getSessionId());
                    }

                }, ackChatObjectData1);
            }
        });

        server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                server.getBroadcastOperations().sendEvent("chatevent", data);
            }
        });

        final SocketIONamespace chat1namespace = server.addNamespace("/chat1");
        chat1namespace.addEventListener("message", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                // broadcast messages to all clients
                chat1namespace.getBroadcastOperations().sendEvent("message", data);
            }
        });

        final SocketIONamespace chat2namespace = server.addNamespace("/chat2");
        chat2namespace.addEventListener("message", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                // broadcast messages to all clients
                chat2namespace.getBroadcastOperations().sendEvent("message", data);
            }
        });

        server.start();
    }


    public void leaveRoom(UUID uuid) {
        SocketIOClient client = server.getClient(uuid);
        client.leaveRoom("11");
        client.sendEvent("leaveroom");
    }

    //小喇叭
    public void boardcast() {
        server.getBroadcastOperations().sendEvent("boardcase", "aaa");
    }

}
