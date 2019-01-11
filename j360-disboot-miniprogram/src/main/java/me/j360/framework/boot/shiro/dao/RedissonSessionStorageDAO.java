package me.j360.framework.boot.shiro.dao;

import lombok.Setter;
import me.j360.framework.common.web.context.SessionUser;
import org.redisson.api.RedissonClient;

/**
 * @author: min_xu
 * @date: 2019/1/11 5:50 PM
 * 说明：
 */
public class RedissonSessionStorageDAO extends AbstractSessionStorageDAO {

    @Setter
    private RedissonClient redissonClient;

    @Override
    public void save(SessionUser sessionUser) {

    }

    @Override
    public SessionUser get(String sessionId) {
        return null;
    }
}
