package me.j360.disboot.miniprogram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 说明：小程序服务端启动入口
 */

@ComponentScan({"me.j360.disboot.miniprogram", "me.j360.framework.boot.error"})
@SpringBootApplication
public class BootstrapApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(BootstrapApplication.class, args);
    }
}
