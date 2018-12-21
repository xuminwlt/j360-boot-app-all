package me.j360.disboot.service;

import me.j360.disboot.base.domain.result.DefaultResult;
import me.j360.disboot.model.domain.User;

/**
 * Package: me.j360.disboot.service
 * User: min_xu
 * Date: 2017/6/1 下午4:16
 * 说明：
 */
public interface UserService {
    DefaultResult<User> getUserById(Long uid);
}
