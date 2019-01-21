package me.j360.disboot.web.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import kamon.annotation.EnableKamon;
import kamon.annotation.Trace;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: min_xu
 * @date: 2018/12/20 4:22 PM
 * 说明：
 */

@EnableKamon
@RestController
public class UserController {

    //@Reference(interfaceClass = UserService.class)
    //private UserService userService;

    @Trace("sayHello")
    @ResponseBody
    @RequestMapping("/sayHello")
    @SentinelResource(value = "sayHello", blockHandler = "handleException", blockHandlerClass = {UserController.class})
    public String sayHello() {
//        DefaultResult<User> result = userService.getUserById(1L);
//        if (result.isSuccess()) {
//            User user = result.getData();
//            return user.getName();
//        }
        return "null";
    }

    // Fallback 函数，函数签名与原函数一致.
    public String helloFallback(long s) {
        return String.format("Halooooo %d", s);
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionHandler(long s, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "Oops, error occurred at " + s;
    }

}
