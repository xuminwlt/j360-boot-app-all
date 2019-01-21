package me.j360.disboot.biz.rocketmq;

import me.j360.disboot.service.BaseJunitTest;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TxService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>01/21/2019</pre>
 */
public class TxServiceTest extends BaseJunitTest {

    @Autowired
    private TxService txService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: createOrderMessage(Message message)
     */
    @Test
    public void testCreateOrderMessage() throws Exception {
        Message message = new Message("TopicTest",
                "test_tag",
                ("Transaction Message ").getBytes(RemotingHelper.DEFAULT_CHARSET));

        txService.createOrderMessage(message);
    }


    @Test
    public void createPushMessage() throws Exception {
        Message message = new Message("TopicTest",
                "test_tag",
                ("Transaction Message ").getBytes(RemotingHelper.DEFAULT_CHARSET));

        txService.createPushMessage(message);
    }

} 
