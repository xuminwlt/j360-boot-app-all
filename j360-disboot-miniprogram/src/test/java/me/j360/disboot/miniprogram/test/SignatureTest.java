package me.j360.disboot.miniprogram.test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import me.j360.disboot.base.constant.AppConfig;
import me.j360.framework.common.util.JwtUtil;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Optional;

/**
 * @author: min_xu
 * @date: 2019/1/11 6:23 PM
 * 说明：
 */
public class SignatureTest {
    Algorithm algorithm = Algorithm.HMAC256("secret");


    @Test
    public void createTest() {
        DateTime dateTime = new DateTime();
        String token = JWT.create()
                .withJWTId("22")
                .withIssuer(AppConfig.JWT_ISSUER)
                .withIssuedAt(dateTime.toDate())
                .withExpiresAt(dateTime.plusYears(1).toDate())
                .withAudience("")
                .withSubject(Optional.ofNullable("").orElse(""))
                .withClaim("nonce", System.currentTimeMillis())
                .sign(algorithm);

        System.out.println("----------");
        System.out.println(token);
        System.out.println("----------");
    }

    @Test
    public void getTest() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIiLCJzdWIiOiIiLCJpc3MiOiJKMzYwIiwiZXhwIjoxNTc4NzM4NDAzLCJpYXQiOjE1NDcyMDI0MDMsIm5vbmNlIjoxNTQ3MjAyNDA0MTYxLCJqdGkiOiIyMiJ9.-TQCW0ZeYJp4Ml5e1lcujqSdSFV8D0dE79Zmd5ur7yw";
        JwtUtil.verify(algorithm, AppConfig.JWT_ISSUER, token);
    }

}
