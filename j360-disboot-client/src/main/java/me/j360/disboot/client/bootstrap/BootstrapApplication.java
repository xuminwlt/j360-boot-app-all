package me.j360.disboot.client.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

/**
 * Package: me.j360.disboot.client.bootstrap
 * User: min_xu
 * Date: 2017/6/2 下午5:20
 * 说明：
 */

@SpringBootApplication
public class BootstrapApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(BootstrapApplication.class,args);

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
}
