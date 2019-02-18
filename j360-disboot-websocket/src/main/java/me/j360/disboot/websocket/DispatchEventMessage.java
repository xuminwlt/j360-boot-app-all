/**
 * Copyright (c) 2012-2019 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.j360.disboot.websocket;

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
