package me.j360.framework.boot.shiro;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vip.vjtools.vjkit.collection.ArrayUtil;
import me.j360.disboot.base.constant.AppConfig;
import me.j360.framework.base.constant.DefaultErrorCode;
import me.j360.framework.common.exception.BizException;
import me.j360.framework.common.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: min_xu
 * @date: 2019/1/11 5:11 PM
 * 说明：
 */
public class JwtSignature {

    private static String Authorization = "Authorization";

    private Algorithm algorithm;

    public JwtSignature(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String getJwt(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader(Authorization);
        if (StringUtils.isEmpty(authorization)) {
            throw BizException.bizException.clone(DefaultErrorCode.AUTH_ACCESS_SESSION_ERROR);
        }
        if(StringUtils.isEmpty(authorization) || ! authorization.startsWith("Bearer ")){
            throw BizException.bizException.clone(DefaultErrorCode.AUTH_ACCESS_SESSION_ERROR);
        }
        return authorization;
    }

    public JwtUtil.JwtOptions decode(String token) {
        DecodedJWT jwt = JwtUtil.verify(algorithm, AppConfig.JWT_ISSUER, token);
        return JwtUtil.JwtOptions.builder().subject(jwt.getSubject())
                .signature(jwt.getSignature())
                .audience(ArrayUtil.toArray(jwt.getAudience(), String.class))
                .cid(jwt.getId())
                .issueAt(jwt.getIssuedAt())
                .issuer(jwt.getIssuer())
                .nonce(jwt.getClaim("nonce").asLong())
                .expiresAt(jwt.getExpiresAt())
                .build();
    }

    public String signature(JwtUtil.JwtOptions options) {
        String credentials = JwtUtil.sigurate(algorithm, options);
        return credentials;
    }



}
