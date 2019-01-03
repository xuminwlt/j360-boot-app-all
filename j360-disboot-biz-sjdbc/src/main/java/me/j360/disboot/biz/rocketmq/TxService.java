package me.j360.disboot.biz.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
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
    @Autowired
    private TransactionMQProducer transactionMQProducer;

    public String createOrderMessage(Message message) {

        try {
            TransactionSendResult result = transactionMQProducer.sendMessageInTransaction(message, null);
            result.getLocalTransactionState();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return null;
    }


}
