package me.j360.framework.boot.shiro.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import me.j360.framework.base.constant.DefaultErrorCode;
import me.j360.framework.base.exception.ServiceException;
import me.j360.framework.boot.shiro.JwtSignature;
import me.j360.framework.boot.shiro.dao.SessionStorageDAO;
import me.j360.framework.boot.token.SessionAuthcToken;
import me.j360.framework.common.util.JwtUtil;
import me.j360.framework.common.web.context.DefaultSessionUser;
import me.j360.framework.common.web.context.SessionContext;
import org.apache.shiro.authc.AuthenticationException;
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
@Slf4j
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
            log.debug("TokenAuthcFilte={}", token);
            throw new ServiceException(DefaultErrorCode.AUTH_ACCESS_SESSION_ERROR, exception);
        }

        DefaultSessionUser sessionUser = (DefaultSessionUser) sessionStorageDAO.get(jwtOptions.getSubject());
        //session timeout
        if (Objects.isNull(sessionUser)) {
            SessionContext.setSessionUser(sessionUser);
            //throw BizException.newBizException(DefaultErrorCode.AUTH_ACCESS_SESSION_ERROR);
        }

        //custom signature
        SessionAuthcToken sessionAuthcToken = new SessionAuthcToken(jwtOptions.getSubject(), jwtOptions.getSubject());
        try {
            Subject subject = getSubject(request, response);
            subject.login(sessionAuthcToken);
        } catch (AuthenticationException e) {
            throw new ServiceException(DefaultErrorCode.AUTH_ACCESS_SESSION_ERROR, e);
        }
        return true;
    }
}
