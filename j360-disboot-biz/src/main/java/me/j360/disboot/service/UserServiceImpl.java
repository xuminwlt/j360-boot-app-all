package me.j360.disboot.service;

import com.alibaba.dubbo.config.annotation.Service;
import me.j360.disboot.base.domain.result.DefaultResult;
import me.j360.disboot.manager.UserManager;
import me.j360.disboot.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Package: me.j360.disboot.service
 * User: min_xu
 * Date: 2017/6/1 下午4:53
 * 说明：
 */

@Service(
        version = "1.0.0",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManager userManager;


    @Override
    public DefaultResult<User> getUserById(Long uid) {
        return DefaultResult.success(userManager.getUserById(uid));
    }
}
