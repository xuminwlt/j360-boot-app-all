
package me.j360.disboot.websocket.manager;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import me.j360.disboot.websocket.configuration.SocketTemplate;
import me.j360.framework.base.constant.DefaultErrorCode;
import me.j360.framework.boot.shiro.JwtSignature;
import me.j360.framework.common.exception.BizException;
import me.j360.framework.common.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 处理会话事件
 */

@Component
public final class SocketSessionManager {

    @Autowired
    private SocketTemplate socketTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private JwtSignature jwtSignature;


    //TODO
    public void onconnect(SocketIOClient client) {
        String jwt = getJwt(client.getHandshakeData());
        //JwtUtil.JwtOptions getOptions
    }
    //TODO
    public void onDisconnect(SocketIOClient client) {
        String jwt = getJwt(client.getHandshakeData());

    }
    //TODO
    public void onPing(SocketIOClient client) {
        String jwt = getJwt(client.getHandshakeData());

    }

    public void handshake(HandshakeData data) {
        String jwt = data.getSingleUrlParam("jwt");
        if (StringUtils.isEmpty(jwt)) {
            throw BizException.newBizException(DefaultErrorCode.AUTH_ACCESS_SESSION_ERROR);
        }
        jwtSignature.checkJwt(jwt);
    }

    private JwtUtil.JwtOptions getOptions(String jwt) {
        JwtUtil.JwtOptions jwtOptions;
        jwtOptions = this.jwtSignature.decode(jwt);
        return jwtOptions;
    }

    private String getJwt(HandshakeData data) {
        String jwt = data.getSingleUrlParam("jwt");
        if (StringUtils.isEmpty(jwt)) {
            throw BizException.newBizException(DefaultErrorCode.AUTH_ACCESS_SESSION_ERROR);
        }
        return jwt;
    }


//    private UserSession getUserFromJwt(String jwt) {
//        JwtUtil.JwtOptions options = getOptions(jwt);
//        return getUser(options.getSubject());
//    }
//
//    private UserSession getUser(String uuid) {
//        redissonClient.getBucket(AppConfig)
//        return null;
//    }
}
