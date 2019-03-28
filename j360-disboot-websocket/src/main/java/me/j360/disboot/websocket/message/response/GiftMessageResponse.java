package me.j360.disboot.websocket.message.response;

import lombok.Data;

/**
 * @author: min_xu
 */

@Data
public class GiftMessageResponse extends BaseMessageResponse {

    private Long senderId;
    private Long toId;
    private Long giftId;

}
