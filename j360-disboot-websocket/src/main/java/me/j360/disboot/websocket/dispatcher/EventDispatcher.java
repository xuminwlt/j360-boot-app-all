
package me.j360.disboot.websocket.dispatcher;


import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.j360.disboot.websocket.constant.SocketEventType;
import me.j360.disboot.websocket.manager.SocketSessionManager;
import me.j360.disboot.websocket.message.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
public final class EventDispatcher  {

    public static final int POLICY_REJECT = 2;
    public static final int POLICY_LOG = 1;
    public static final int POLICY_IGNORE = 0;
    private final Map<String, MessageHandler> handlers = new HashMap<>();
    private final int unsupportedPolicy;

    @Setter
    private SocketSessionManager sessionManager;

    public EventDispatcher() {
        unsupportedPolicy = POLICY_REJECT;
    }

    public EventDispatcher(int unsupportedPolicy) {
        this.unsupportedPolicy = unsupportedPolicy;
    }

    public void register(SocketEventType eventType, MessageHandler handler) {
        handlers.put(eventType.name(), handler);
    }

    public void register(SocketEventType eventType, Supplier<MessageHandler> handlerSupplier, boolean enabled) {
        if (enabled && !handlers.containsKey(eventType.name())) {
            register(eventType, handlerSupplier.get());
        }
    }

    public void register(SocketEventType eventType, Supplier<MessageHandler> handlerSupplier) {
        this.register(eventType, handlerSupplier, true);
    }

    public MessageHandler unRegister(Command command) {
        return handlers.remove(command.getCmd());
    }

    public void onReceive(SocketIOClient client, Command command, final AckRequest ackRequest) {
        MessageHandler handler = handlers.get(command.getCmd());
        if (handler != null) {
            try {
                handler.handle(client, command, ackRequest);
            } catch (Throwable throwable) {

            }
        } else {
            if (unsupportedPolicy > POLICY_IGNORE) {

            }
        }
    }

    public void handshake(HandshakeData data) {
        sessionManager.handshake(data);
    }

    public void onconnect(SocketIOClient client) {
        sessionManager.onconnect(client);
    }

    public void onDisconnect(SocketIOClient client) {
        sessionManager.onDisconnect(client);
    }

    public void onPing(SocketIOClient client) {
        sessionManager.onPing(client);
    }
}
