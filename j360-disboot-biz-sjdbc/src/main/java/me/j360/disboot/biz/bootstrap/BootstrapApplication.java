package me.j360.disboot.biz.bootstrap;

import kamon.Kamon;
import kamon.prometheus.PrometheusReporter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Package: me.j360.disboot.bootstrap
 * User: min_xu
 * Date: 2017/6/1 下午6:02
 * 说明：
 */

@ComponentScan("me.j360.disboot.biz")
@MapperScan("me.j360.disboot.biz.mapper")
@SpringBootApplication
public class BootstrapApplication {

    public static void main(String[] args) {
        Kamon.addReporter(new PrometheusReporter());
        SpringApplication.run(BootstrapApplication.class, args);
    }

}
