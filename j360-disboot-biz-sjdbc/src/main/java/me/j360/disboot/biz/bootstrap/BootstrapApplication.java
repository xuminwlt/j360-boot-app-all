package me.j360.disboot.biz.bootstrap;

import org.mybatis.spring.annotation.MapperScan;
import org.rocketmq.starter.annotation.EnableRocketMQ;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PreDestroy;

/**
 * Package: me.j360.disboot.bootstrap
 * User: min_xu
 * Date: 2017/6/1 下午6:02
 * 说明：
 */


@ComponentScan("me.j360.disboot.biz")
@MapperScan("me.j360.disboot.biz.mapper")
@SpringBootApplication
@EnableRocketMQ
public class BootstrapApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootstrapApplication.class, args);
    }

    @PreDestroy
    void shutdown() {

    }

}
