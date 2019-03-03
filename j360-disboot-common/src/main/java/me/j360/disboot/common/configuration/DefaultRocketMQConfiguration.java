package me.j360.disboot.common.configuration;

import me.j360.disboot.common.rocketmq.RocketMQTransactionListener;
import me.j360.framework.boot.rocketmq.AbstractRocketMQConfiguration;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.springframework.context.annotation.Configuration;

/**
 * @author: min_xu
 */
@Configuration
public class DefaultRocketMQConfiguration extends AbstractRocketMQConfiguration {

    @Override
    protected TransactionListener newTxListener() {
        return new RocketMQTransactionListener();
    }

}
