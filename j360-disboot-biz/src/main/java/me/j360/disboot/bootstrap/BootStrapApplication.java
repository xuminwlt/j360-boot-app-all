package me.j360.disboot.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Package: me.j360.disboot.bootstrap
 * User: min_xu
 * Date: 2017/6/1 下午6:02
 * 说明：
 */


@EnableAutoConfiguration
@ComponentScan("me.j360.disboot")
public class BootStrapApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootStrapApplication.class, args);
    }
}
