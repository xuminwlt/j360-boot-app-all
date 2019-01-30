package me.j360.disboot.websocket;

import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

/**
 * @author: min_xu
 * @date: 2019/1/30 6:30 PM
 * 说明：向指定的node-server发送消息
 */

public class RedissonTest {


    private RedissonClient redisson;
    private RTopic topic;

    @Before
    public void before() {
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        redisson = Redisson.create(config);

        topic = redisson.getTopic("node-1", StringCodec.INSTANCE);
        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence channel, String msg) {
                System.out.println(msg);
            }
        });
    }

    @Test
    public void testPubsub() {
        topic.publish("hello node1");
    }

}
