package me.j360.framework.boot.shiro.realm;

import me.j360.framework.boot.shiro.JwtSignature;
import me.j360.framework.boot.shiro.dao.SessionStorageDAO;
import me.j360.framework.boot.token.SessionAuthcToken;
import me.j360.framework.common.web.context.DefaultSessionUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Objects;

/**
 * @author: min_xu
 * @date: 2019/1/10 10:49 PM
 * 说明：ROLE: GUEST/USER
 */
public class TokenRealm extends AuthorizingRealm {


    private SessionStorageDAO sessionStorageDAO;
    private JwtSignature jwtSignature;

    public TokenRealm(SessionStorageDAO sessionStorageDAO) {
        this.sessionStorageDAO = sessionStorageDAO;
        this.jwtSignature = jwtSignature;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof SessionAuthcToken;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String sessionId = (String) principals.getPrimaryPrincipal();
        DefaultSessionUser sessionUser = (DefaultSessionUser) sessionStorageDAO.get(sessionId);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (Objects.nonNull(sessionUser)) {
            authorizationInfo.setRoles(sessionUser.getRoles());
            authorizationInfo.setStringPermissions(sessionUser.getRoles());
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String sessionId = (String) token.getPrincipal();
        //DefaultSessionUser sessionUser = (DefaultSessionUser) sessionStorageDAO.get(sessionId);
        String sign = (String) token.getCredentials();
        //no more verify
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                sessionId,
                sign,
                getName());
        return authenticationInfo;
    }


}
