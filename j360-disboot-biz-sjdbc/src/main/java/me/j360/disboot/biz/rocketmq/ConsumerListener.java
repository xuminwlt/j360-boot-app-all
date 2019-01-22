package me.j360.disboot.biz.rocketmq;

import org.rocketmq.starter.annotation.RocketMQListener;
import org.rocketmq.starter.annotation.RocketMQMessage;
import org.springframework.stereotype.Service;

/**
 * @author: min_xu
 * @date: 2019/1/22 1:34 PM
 * 说明：
 */
@Service
@RocketMQListener(topic = "test")
public class ConsumerListener {


    @RocketMQMessage(messageClass = String.class, tag = "PUSH")
    public void onMessage(String message) {
        System.out.println("received message: {" + message + "}");
    }

}
