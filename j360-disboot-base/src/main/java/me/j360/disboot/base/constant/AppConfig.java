package me.j360.disboot.base.constant;

/**
 * @author: min_xu
 * @date: 2019/1/10 5:18 PM
 * 说明：
 */
public abstract class AppConfig {

    public static final String REDIS_PREFIX_FLODER = "j360:";
    public static final String USER_SESSION = REDIS_PREFIX_FLODER + "u:sess:%s";

    //Auth 定义jwt内容
    public static final String JWT_AUD_GUEST = "guest";
    public static final String JWT_AUD_USET = "user";
    public static final String JWT_ISSUER = "J360";

}
