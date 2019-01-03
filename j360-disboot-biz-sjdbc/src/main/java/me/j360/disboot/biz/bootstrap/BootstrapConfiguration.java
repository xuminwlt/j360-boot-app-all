package me.j360.disboot.biz.bootstrap;

import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallbackRegistry;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.dubbo.rpc.RpcResult;
import me.j360.disboot.biz.rocketmq.TxListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Package: me.j360.disboot.bootstrap
 * User: min_xu
 * Date: 2017/6/1 下午6:15
 * 说明：
 */

@Configuration
public class BootstrapConfiguration {

    @PostConstruct
    public void init() {
        registerFallback();
    }


    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }


    private static void registerFallback() {
        // Register fallback handler for consumer.
        // If you only want to handle degrading, you need to
        // check the type of BlockException.
        DubboFallbackRegistry.setConsumerFallback((a, b, ex) ->
                new RpcResult("Error: " + ex.getClass().getTypeName()));
    }


    @Bean
    public TransactionMQProducer transactionMQProducer() {
        TransactionMQProducer transactionMQProducer = new TransactionMQProducer();
        transactionMQProducer.setTransactionListener(new TxListener());
        return transactionMQProducer;
    }

}
