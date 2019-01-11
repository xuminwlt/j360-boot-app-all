package me.j360.framework.boot.shiro.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import me.j360.framework.base.constant.DefaultErrorCode;
import me.j360.framework.base.exception.ServiceException;
import me.j360.framework.boot.shiro.JwtSignature;
import me.j360.framework.boot.shiro.dao.SessionStorageDAO;
import me.j360.framework.common.exception.BizException;
import me.j360.framework.common.util.JwtUtil;
import me.j360.framework.common.web.context.DefaultSessionUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Assert;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author: min_xu
 * @date: 2019/1/10 10:49 PM
 * 说明：
 */
public class TokenAuthcFilter extends AccessControlFilter {

    private JwtSignature jwtSignature;
    private SessionStorageDAO sessionStorageDAO;

    public TokenAuthcFilter(JwtSignature jwtSignature, SessionStorageDAO sessionStorageDAO) {
        super();
        this.jwtSignature = jwtSignature;
        this.sessionStorageDAO = sessionStorageDAO;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token = jwtSignature.getJwt((HttpServletRequest) request);

        Assert.notNull(token);
        JwtUtil.JwtOptions jwtOptions;
        try {
            jwtOptions = jwtSignature.decode(token);
        } catch (JWTVerificationException exception){
            //Invalid common signature/claims
            throw new ServiceException(DefaultErrorCode.AUTH_ACCESS_SESSION_ERROR, exception);
        }

        DefaultSessionUser sessionUser = (DefaultSessionUser) sessionStorageDAO.get(jwtOptions.getSubject());
        //session timeout
        if (Objects.isNull(sessionUser)) {
            throw BizException.newBizException(DefaultErrorCode.AUTH_ACCESS_SESSION_ERROR);
        }

        //custom signature
        AuthenticationToken authenticationToken = new AuthenticationToken() {
            @Override
            public Object getPrincipal() {
                return jwtOptions.getSubject();
            }
            @Override
            public Object getCredentials() {
                return jwtOptions;
            }
        };
        try {
            Subject subject = getSubject(request, response);
            subject.login(authenticationToken);
        } catch (AuthenticationException e) {
            throw new ServiceException(DefaultErrorCode.AUTH_ACCESS_SESSION_ERROR, e);
        }
        return true;
    }
}
