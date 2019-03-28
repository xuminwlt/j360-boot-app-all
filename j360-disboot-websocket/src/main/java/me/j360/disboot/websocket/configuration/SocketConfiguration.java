package me.j360.disboot.websocket.configuration;

import com.auth0.jwt.algorithms.Algorithm;
import me.j360.disboot.websocket.constant.SocketEventType;
import me.j360.disboot.websocket.dispatcher.EventDispatcher;
import me.j360.disboot.websocket.manager.BizEventHandler;
import me.j360.disboot.websocket.manager.SocketSessionManager;
import me.j360.framework.boot.shiro.JwtSignature;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: min_xu
 */

@Configuration
public class SocketConfiguration {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private JwtSignature jwtSignature;
    @Autowired
    private BizEventHandler bizEventHandler;
    @Autowired
    private SocketSessionManager sessionManager;


    @Value("${socket.port}")
    private int port;

    @Bean
    public Algorithm algorithm() {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return algorithm;
    }

    @Bean
    public EventDispatcher eventDispatcher() {
        EventDispatcher eventDispatcher = new EventDispatcher();
        eventDispatcher.setSessionManager(sessionManager);
        for (SocketEventType eventType : SocketEventType.values()) {
            eventDispatcher.register(eventType, bizEventHandler);
        }
        return eventDispatcher;
    }

    @Bean(destroyMethod = "stop")
    public SocketServer socketServer(@Autowired EventDispatcher eventDispatcher) {
        SocketServer socketServer = new SocketServer(eventDispatcher, redissonClient);
        socketServer.init(port);
        socketServer.start();
        return socketServer;
    }
}
