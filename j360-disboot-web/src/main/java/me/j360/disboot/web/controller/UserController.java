package me.j360.disboot.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import me.j360.disboot.base.domain.result.DefaultResult;
import me.j360.disboot.model.domain.User;
import me.j360.disboot.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: min_xu
 * @date: 2018/12/20 4:22 PM
 * 说明：
 */


@RestController
public class UserController {

    @Reference(interfaceClass = UserService.class)
    private UserService userService;

    @ResponseBody
    @RequestMapping("/sayHello")
    public String sayHello() {
        DefaultResult<User> result = userService.getUserById(1L);
        return result.getData().toString();
    }

}
