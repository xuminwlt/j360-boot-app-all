package me.j360.disboot.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author: min_xu
 * @date: 2019/1/31 10:18 AM
 * 说明：当webscoket建立连接的时候被拦截，获取当前应用的session，将用户登录信息获取出来，如果用户未登录，
 * 拒绝连接，如果已经登陆了，那么将用户绑定到stomp的session中，UserInterceptor调用了这个用户信息
 */

@Slf4j
public class SessionAuthHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        HttpSession session = getSession(request);
        //from request header jwt
        if(session == null || session.getAttribute(Constants.SESSION_USER) == null){
            log.error("websocket权限拒绝");
//            return false;
            throw new RuntimeException("websocket权限拒绝");
        }
        attributes.put(Constants.WEBSOCKET_USER_KEY, session.getAttribute(Constants.SESSION_USER));
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession(false);
        }
        return null;
    }
}