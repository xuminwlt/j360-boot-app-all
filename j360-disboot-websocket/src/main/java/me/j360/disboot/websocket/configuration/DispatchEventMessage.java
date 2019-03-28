
package me.j360.disboot.websocket.configuration;

import com.corundumstudio.socketio.protocol.Packet;
import com.corundumstudio.socketio.store.pubsub.PubSubMessage;
import lombok.ToString;

@ToString
public class DispatchEventMessage extends PubSubMessage {

    private static final long serialVersionUID = 6692047718303934349L;

    private String uuid;
    private Packet packet;

    public DispatchEventMessage() {
    }

    public DispatchEventMessage(String uuid, Packet packet) {
        this.uuid = uuid;
        this.packet = packet;
    }


    public Packet getPacket() {
        return packet;
    }

    public String getUuid() {
        return uuid;
    }

}
