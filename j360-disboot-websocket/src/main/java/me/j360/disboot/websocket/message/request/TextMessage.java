package me.j360.disboot.websocket.message.request;

import lombok.Data;

/**
 * @author: min_xu
 */

@Data
public class TextMessage extends BaseMessageRequest {

    private String message;
}
