package me.j360.disboot.biz.bootstrap;

import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallbackRegistry;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import me.j360.disboot.biz.rocketmq.TxListener;
import org.apache.dubbo.rpc.RpcResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.rocketmq.starter.configuration.RocketMQProperties;
import org.rocketmq.starter.core.producer.RocketMQProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * Package: me.j360.disboot.bootstrap
 * User: min_xu
 * Date: 2017/6/1 下午6:15
 * 说明：
 */

@Configuration
public class BootstrapConfiguration {


    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }





    @Autowired
    private RocketMQProperties rocketMQProperties;


    @Bean
    public RocketMQProducerTemplate rocketMQProducerTemplate() throws MQClientException {
        RocketMQProducerTemplate producer = new RocketMQProducerTemplate();
        producer.setProducerGroup(rocketMQProperties.getProducer().getGroup());
        producer.setTimeOut(rocketMQProperties.getProducer().getSendMsgTimeout());
        producer.setOrderlyMessage(true);
        producer.setMessageClass(String.class);
        producer.setNamesrvAddr(rocketMQProperties.getNameServer());
        return producer;
    }

    @Bean
    public TransactionMQProducer transactionMQProducer() throws MQClientException {
        TransactionMQProducer producer = new TransactionMQProducer();
        RocketMQProperties.Producer producerConfig = rocketMQProperties.getProducer();
        String groupName = producerConfig.getGroup();
        Assert.hasText(groupName, "[spring.rocketmq.producer.group] must not be null");

        producer.setNamesrvAddr(rocketMQProperties.getNameServer());
        producer.setSendMsgTimeout(producerConfig.getSendMsgTimeout());
        producer.setRetryTimesWhenSendFailed(producerConfig.getRetryTimesWhenSendFailed());
        producer.setRetryTimesWhenSendAsyncFailed(producerConfig.getRetryTimesWhenSendAsyncFailed());
        producer.setMaxMessageSize(producerConfig.getMaxMessageSize());
        producer.setCompressMsgBodyOverHowmuch(producerConfig.getCompressMsgBodyOverHowmuch());
        producer.setRetryAnotherBrokerWhenNotStoreOK(producerConfig.isRetryAnotherBrokerWhenNotStoreOk());
        producer.setTransactionListener(new TxListener());
        producer.setProducerGroup(rocketMQProperties.getProducer().getGroup());
        producer.start();
        return producer;
    }
}
