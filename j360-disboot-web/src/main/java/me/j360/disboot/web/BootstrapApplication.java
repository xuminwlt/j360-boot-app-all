package me.j360.disboot.web;

import kamon.Kamon;
import kamon.prometheus.PrometheusReporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Package: me.j360.disboot.web.bootstrap
 * User: min_xu
 * Date: 2017/6/1 下午10:54
 * 说明：
 */
@SpringBootApplication
@ComponentScan("me.j360.disboot.web")
public class BootstrapApplication {

    public static void main(String[] args) {
        Kamon.addReporter(new PrometheusReporter());
        SpringApplication.run(BootstrapApplication.class, args);
    }

}
