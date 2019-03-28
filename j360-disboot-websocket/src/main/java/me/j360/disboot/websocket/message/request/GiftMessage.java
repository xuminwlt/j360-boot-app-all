package me.j360.disboot.websocket.message.request;

import lombok.Data;

/**
 * @author: min_xu
 */

@Data
public class GiftMessage extends BaseMessageRequest {

    private Long toId;
    private Long giftId;

}
