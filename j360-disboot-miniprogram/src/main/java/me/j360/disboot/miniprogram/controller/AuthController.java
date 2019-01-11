package me.j360.disboot.miniprogram.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.j360.disboot.miniprogram.domain.response.UserSessionResponse;
import me.j360.disboot.miniprogram.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author: min_xu
 * @date: 2018/1/15 下午1:56
 * 说明：
 */

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserManager userManager;

    @ApiOperation(value = "访客授权接口", notes = "登录参数?cid(uuid类型)", code = 200, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/guest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserSessionResponse guest(@Validated @RequestParam @NotNull String cid) {
        UserSessionResponse response = new UserSessionResponse();
        boolean register = false;
        response.setJwt(userManager.authGuest(cid));
        response.setRegister(register);
        return response;
    }

    @ApiOperation(value = "小程序授权登录接口", notes = "登录参数?code(小程序code)&cid=uuid", code = 200, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserSessionResponse login(@RequestParam @NotNull String code, @RequestParam @NotNull String cid) {

        UserSessionResponse response = new UserSessionResponse();
        //执行注册或者登陆步骤
        String uuid = UUID.randomUUID().toString();
        String jwt = userManager.authUser(cid, uuid);
        boolean register = true; //userManager.saveUser(thirdContext, uuid);
        response.setJwt(jwt);
        response.setRegister(register);
        return response;
    }




}
