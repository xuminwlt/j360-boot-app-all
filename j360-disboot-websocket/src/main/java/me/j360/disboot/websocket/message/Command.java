package me.j360.disboot.websocket.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: min_xu
 * 通用消息
 */

@Data
public class Command implements Serializable {

    private String cmd;
    private Object data;
}
