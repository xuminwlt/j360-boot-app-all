package me.j360.disboot.websocket.message.response;

import lombok.Data;

/**
 * @author: min_xu
 */

@Data
public class TextMessageResponse extends BaseMessageResponse  {

    private Long senderId;
    private String senderName;
    private String message;

}
