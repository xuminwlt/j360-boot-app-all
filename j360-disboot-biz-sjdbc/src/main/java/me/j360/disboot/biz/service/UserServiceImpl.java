package me.j360.disboot.biz.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import me.j360.disboot.biz.manager.UserManager;
import me.j360.disboot.model.domain.User;
import me.j360.disboot.service.UserService;
import me.j360.framework.base.domain.rpc.result.DefaultResult;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Package: me.j360.disboot.service
 * User: min_xu
 * Date: 2017/6/1 下午4:53
 * 说明：
 */

@Service(
        interfaceClass = UserService.class
)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManager userManager;

    @SentinelResource(value = "getUserById", blockHandler = "exceptionHandler", fallback = "helloFallback")
    @Override
    public DefaultResult<User> getUserById(Long uid) {
        return DefaultResult.success(userManager.getUserById(uid));
    }

    // Fallback 函数，函数签名与原函数一致.
    public DefaultResult<User> getUserByIdFallback(Long uid) {
        return DefaultResult.success();
    }
}
