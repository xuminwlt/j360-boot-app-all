
package me.j360.disboot.websocket.dispatcher;


import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import me.j360.disboot.websocket.message.Command;

public interface MessageHandler {
    void handle(SocketIOClient client, Command command, final AckRequest ackRequest);
}
