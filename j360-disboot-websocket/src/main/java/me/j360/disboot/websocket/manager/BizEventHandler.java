
package me.j360.disboot.websocket.manager;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import me.j360.disboot.websocket.configuration.SocketTemplate;
import me.j360.disboot.websocket.dispatcher.MessageHandler;
import me.j360.disboot.websocket.message.Command;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 处理业务上定义的消息事件
 */
public final class BizEventHandler implements MessageHandler {

    @Autowired
    private SocketTemplate socketTemplate;
    @Autowired
    private RedissonClient redissonClient;


    @Override
    public void handle(SocketIOClient client, Command command, final AckRequest ackRequest) {

    }
}
