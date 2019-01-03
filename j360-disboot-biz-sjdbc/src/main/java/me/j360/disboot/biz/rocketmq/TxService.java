package me.j360.disboot.biz.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: min_xu
 * @date: 2019/1/3 3:01 PM
 * 说明：
 */

@Component
public class TxService {

    @Autowired
    private DefaultMQProducer mqProducer;



}
