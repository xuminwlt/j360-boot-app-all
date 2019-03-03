package me.j360.disboot.common.rocketmq;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.j360.framework.base.constant.DefaultErrorCode;
import me.j360.framework.base.exception.ServiceException;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.rocketmq.starter.core.producer.RocketMQProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: min_xu
 */

@Slf4j
@Component
public class MQProducter {

    @Autowired
    private TransactionMQProducer transactionMQProducer;
    @Autowired
    private RocketMQProducerTemplate rocketMQProducerTemplate;

    public void create(String topic, String tag, Object object) {

    }

    public void createTransaction() {

    }


    //异步确保型事务, 发送失败抛出异常
    public void createOrderMessage(Message message) {
        message.setTags("ORDER");
        try {
            TransactionSendResult result = transactionMQProducer.sendMessageInTransaction(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("{}", sendResult);
                }
                @Override
                public void onException(Throwable e) {
                    log.error("传输失败", e);
                }
            });
        } catch (MQClientException e) {
            throw new ServiceException(DefaultErrorCode.SYSTEM_ERROR, e);
        }
    }

    //常规消息
    public void createPushMessage(Message message) {
        message.setTags("PUSH");
        try {
            rocketMQProducerTemplate.send(message);
        } catch (MQBrokerException | MQClientException | RemotingException | InterruptedException e) {
            throw new ServiceException(DefaultErrorCode.SYSTEM_ERROR, e);
        }
    }

    @SneakyThrows
    private Message covertToMessage() {
        Message message = new Message("test",
                "test_tag",
                ("Transaction Message ").getBytes("UTF-8"));

        return message;
    }

}
