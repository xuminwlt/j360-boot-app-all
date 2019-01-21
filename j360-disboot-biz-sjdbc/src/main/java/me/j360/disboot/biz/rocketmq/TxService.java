package me.j360.disboot.biz.rocketmq;

import lombok.extern.slf4j.Slf4j;
import me.j360.framework.base.constant.DefaultErrorCode;
import me.j360.framework.base.exception.ServiceException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: min_xu
 * @date: 2019/1/3 3:01 PM
 * 说明：
 */

@Slf4j
@Component
public class TxService {

    @Autowired
    private DefaultMQProducer mqProducer;
    @Autowired
    private TransactionMQProducer transactionMQProducer;

    public void createOrderMessage(Message message) {

        try {
            TransactionSendResult result = transactionMQProducer.sendMessageInTransaction(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("传输成功");
                    log.info("{}", sendResult);
                }
                @Override
                public void onException(Throwable e) {
                    log.error("传输失败", e);
                }
            });

            result.getLocalTransactionState();
            //TODO set biz state from result


        } catch (MQClientException e) {
            throw new ServiceException(DefaultErrorCode.SYSTEM_ERROR, e);
        }
    }


    public void createPushMessage(Message message) {
        try {
            mqProducer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {

                }

                @Override
                public void onException(Throwable e) {

                }
            });
        } catch (MQClientException | RemotingException | InterruptedException e) {
            throw new ServiceException(DefaultErrorCode.SYSTEM_ERROR, e);
        }
    }



}
