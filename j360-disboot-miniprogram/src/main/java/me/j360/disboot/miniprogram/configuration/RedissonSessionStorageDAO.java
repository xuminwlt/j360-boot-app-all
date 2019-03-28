package me.j360.disboot.miniprogram.configuration;

import com.vip.vjtools.vjkit.mapper.JsonMapper;
import me.j360.disboot.base.constant.AppConfig;
import me.j360.framework.boot.shiro.dao.SessionStorageDAO;
import me.j360.framework.common.web.context.DefaultSessionUser;
import me.j360.framework.common.web.context.SessionUser;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: min_xu
 */

@Component
public class RedissonSessionStorageDAO implements SessionStorageDAO {

    @Autowired
    private RedissonClient redissonClient;

    private static String sessionKeyPrefix = AppConfig.USER_SESSION;

    public RedissonSessionStorageDAO() {

    }

    public void save(SessionUser sessionUser) {
        RBucket<String> sessionBucket = redissonClient.getBucket(String.format(sessionKeyPrefix, ((DefaultSessionUser) sessionUser).getSessionId()), StringCodec.INSTANCE);
        sessionBucket.set(JsonMapper.INSTANCE.toJson(sessionUser), 365, TimeUnit.DAYS);
    }

    public SessionUser get(String sessionId) {
        RBucket<String> sessionBucket = redissonClient.getBucket(String.format(sessionKeyPrefix, sessionId), StringCodec.INSTANCE);
        DefaultSessionUser defaultSessionUser = JsonMapper.INSTANCE.fromJson(sessionBucket.get(), DefaultSessionUser.class);
        return defaultSessionUser;
    }

    public Set<String> roles(Long principal) {
        return null;
    }

    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

}
