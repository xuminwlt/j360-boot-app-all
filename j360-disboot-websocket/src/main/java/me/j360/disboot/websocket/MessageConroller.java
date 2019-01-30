package me.j360.disboot.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: min_xu
 * @date: 2019/1/30 5:21 PM
 * 说明：
 */
@Controller
public class MessageConroller {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @ResponseBody
    @RequestMapping("/test")
    public String sendMessage() {
        simpMessageSendingOperations.convertAndSendToUser("1", "/message", "测试convertAndSendToUser");
        simpMessageSendingOperations.convertAndSend("/topic/sendtouser", "测试SimpMessageSendingOperations ");
        return "success";
    }

//    @MessageMapping("/marco")
//    public void handleShout(Shout incoming) {
//        System.out.println("Received message:"+incoming.getMessage());
//    }

    @MessageMapping("/marco")
    @SendTo("/queue/marco")
    public Shout handleShout(Shout incoming) {
        System.out.println("Received message:"+incoming.getMessage());
        Shout  outing = new Shout();
        outing.setMessage("Polo");
        return outing;
    }

    @MessageMapping("/message")
    @SendToUser("/topic/sendtouser")
    public Shout message()
    {
        Shout  outing = new Shout();
        outing.setMessage("SendToUser");
        return outing;
    }

    @SubscribeMapping("/subscribe")
    public Shout handleSubscribe() {
        Shout  outing = new Shout();
        outing.setMessage("subscribes");
        return outing;
    }


    @MessageMapping("bar.{baz}")
    @SendTo("/topic/greetings")
    public String handle1(@DestinationVariable String baz) {

        return baz;
    }

}