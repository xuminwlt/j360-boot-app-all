package me.j360.disboot.miniprogram.manager;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import me.j360.disboot.base.constant.AppConfig;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author: min_xu
 * @date: 2019/1/11 1:39 PM
 * 说明：
 */

@Service
public class UserManager {

    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256("secret");
    }

    public String authGuest(String cid) {
        try {
            DateTime dateTime = new DateTime();
            String token = JWT.create()
                    .withJWTId(cid)
                    .withIssuer(AppConfig.JWT_ISSUER)
                    .withIssuedAt(dateTime.toDate())
                    .withExpiresAt(dateTime.plusYears(1).toDate())
                    .withAudience(AppConfig.JWT_AUD_GUEST)
                    .withSubject("")
                    .withClaim("nonce", System.currentTimeMillis())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            throw new RuntimeException("authGuest ex", exception);
        }
    }

    public String authUser(String cid, String uuid) {
        try {
            DateTime dateTime = new DateTime();
            String token = JWT.create()
                    .withJWTId(cid)
                    .withIssuer(AppConfig.JWT_ISSUER)
                    .withIssuedAt(dateTime.toDate())
                    .withExpiresAt(dateTime.plusDays(1).toDate())
                    .withAudience(AppConfig.JWT_AUD_USET)
                    .withSubject(uuid)
                    .withClaim("nonce", System.currentTimeMillis())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            //Invalid R configuration / Couldn't convert Claims.
            throw new RuntimeException("", exception);
        }
    }
}
