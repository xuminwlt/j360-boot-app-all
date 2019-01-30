package me.j360.disboot.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author: min_xu
 * @date: 2019/1/30 4:59 PM
 * 说明：
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //表明在topic、queue、users这三个域上可以向客户端发消息。
        config.enableSimpleBroker("/topic", "/queue", "/users");
        //客户端向服务端发起请求时，需要以/app为前缀。
        config.setApplicationDestinationPrefixes("/app");
        //给指定用户发送一对一的消息前缀是/users/。
        config.setUserDestinationPrefix("/users/");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //为/j360路径启用SockJS功能
        registry.addEndpoint("/j360").withSockJS();

        /**
         registry.addEndpoint("/ws/icc/websocket")
         .addInterceptors(sessionAuthHandshakeInterceptor)
         .setHandshakeHandler(myDefaultHandshakeHandler)
         .withSockJS();
         */
    }

}

