package me.j360.disboot.service;

import me.j360.disboot.model.domain.User;
import me.j360.framework.base.domain.rpc.result.DefaultResult;

/**
 * Package: me.j360.disboot.service
 * User: min_xu
 * Date: 2017/6/1 下午4:16
 * 说明：
 */
public interface UserService {
    DefaultResult<User> getUserById(Long uid);
}
