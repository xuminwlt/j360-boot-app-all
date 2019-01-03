package me.j360.disboot.biz.rocketmq;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author: min_xu
 * @date: 2019/1/3 3:05 PM
 * 说明：
 */
public class TxListener implements TransactionListener {

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        //TODO insert msgId in biz dbs

        return LocalTransactionState.UNKNOW;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        //TODO select biz info from biz dbs

        return LocalTransactionState.COMMIT_MESSAGE;
    }

}
