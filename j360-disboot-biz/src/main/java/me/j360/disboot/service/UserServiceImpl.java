package me.j360.disboot.service;

import me.j360.disboot.manager.UserManager;
import me.j360.disboot.model.domain.User;
import me.j360.dubbo.base.model.result.DefaultResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Package: me.j360.disboot.service
 * User: min_xu
 * Date: 2017/6/1 下午4:53
 * 说明：
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManager userManager;


    @Override
    public DefaultResult<User> getUserById(Long uid) {
        return DefaultResult.success(userManager.getUserById(uid));
    }
}
