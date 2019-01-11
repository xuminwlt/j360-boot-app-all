package me.j360.framework.boot.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author: min_xu
 * @date: 2019/1/11 6:40 PM
 * 说明：
 */
public class SessionAuthcToken implements AuthenticationToken {


    private String sessionId;
    private String sign;

    public SessionAuthcToken(String sessionId, String sign) {
        this.sessionId = sessionId;
        this.sign = sign;
    }

    @Override
    public Object getPrincipal() {
        return sessionId;
    }

    @Override
    public Object getCredentials() {
        return sign;
    }
}
