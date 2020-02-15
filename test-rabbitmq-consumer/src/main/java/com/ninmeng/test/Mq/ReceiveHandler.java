package com.ninmeng.test.Mq;

import com.ninmeng.test.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by 赵晟晖 on 2020/2/15.
 */
@Component
public class ReceiveHandler {
    //监听email队列
     @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_EMAIL})
     public void receive_email(String msg,Message message,
    Channel channel){
     System.out.println(msg);
    }
    //监听email队列
     @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_SMS})
     public void receive_sms(String msg,Message message,
    Channel channel){
     System.out.println(msg);
    }
}
