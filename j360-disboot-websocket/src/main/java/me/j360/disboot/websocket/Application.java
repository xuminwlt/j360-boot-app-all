package me.j360.disboot.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: min_xu
 * @date: 2019/1/30 5:01 PM
 * 说明：
 */
@SpringBootApplication
public class Application implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private SocketServer socketServer;

    @Override
    public void run(String... args) throws Exception {
        socketServer.start();
    }
}