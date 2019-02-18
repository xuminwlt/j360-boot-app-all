package me.j360.disboot.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: min_xu
 * @date: 2019/1/30 5:01 PM
 * 说明：Socket服务集群环境
 */
@Configuration
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean("socketServer9092")
    public SocketServer socketServer1() {
        SocketServer socketServer = new SocketServer();
        socketServer.start(9092);
        return socketServer;
    }

    @Bean("socketServer9093")
    public SocketServer socketServer2() {
        SocketServer socketServer = new SocketServer();
        socketServer.start(9093);
        return socketServer;
    }
}