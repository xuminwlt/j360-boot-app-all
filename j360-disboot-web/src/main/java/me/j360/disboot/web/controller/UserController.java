package me.j360.disboot.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import me.j360.disboot.service.UserService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: min_xu
 * @date: 2018/12/20 4:22 PM
 * 说明：
 */


@RestController
public class UserController {

    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:12345")
    private UserService userService;


}
