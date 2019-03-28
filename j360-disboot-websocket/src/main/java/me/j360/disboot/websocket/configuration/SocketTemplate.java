package me.j360.disboot.websocket.configuration;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.protocol.Packet;
import com.corundumstudio.socketio.protocol.PacketType;
import lombok.extern.slf4j.Slf4j;
import me.j360.disboot.websocket.constant.PubSubEventType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author: min_xu
 */
@Slf4j
public class SocketTemplate {

    @Autowired
    private SocketServer socketServer;

    public void sendToUser() {

    }

    public void sendToRoom() {

    }

    public void sendToAll() {

    }

    public void joinRoom() {

    }

    public void leaveRoom() {

    }


    public void sendAnyone(String uuid, String event, Object... data) {
        if (!checkEvent(event)) {
            log.error("不支持的消息类型: {}", event );
            return;
        }
        Packet packet = new Packet(PacketType.MESSAGE);
        packet.setSubType(PacketType.EVENT);
        packet.setName(event);
        packet.setData(Arrays.asList(data));

        DispatchEventMessage message = new DispatchEventMessage(uuid, packet);
        socketServer.getEventStore().publish(PubSubEventType.valueOf(event), message);

    }

    private boolean checkEvent(String event) {
        try {
            PubSubEventType.valueOf(event);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void leaveRoom(UUID uuid) {
        SocketIOClient client = socketServer.getServer().getClient(uuid);
        client.leaveRoom("11");
        client.sendEvent("leaveroom");
    }

    //小喇叭
    public void boardcast() {
        socketServer.getServer().getBroadcastOperations().sendEvent("chatevent", "");
    }
}
