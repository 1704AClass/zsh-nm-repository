package com.ningmeng.test.tests;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by 赵晟晖 on 2020/2/14.
 */
public class Producer02_publish {
    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    //交换机
    private static final String EXCHANGE_FANOUT_INFORM="exchange_fanout_inform";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection =null;
        Channel channel = null;
        try {
            //创建rabbitmq连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setVirtualHost("/");//rabbitmq默认虚拟机名称为“/”，虚拟机相当于一个独立的mq服务 器
//创建与rabbitmq服务的tcp连接
            connection = factory.newConnection();
            //创建与exchange的通道，每个连接可以创建多个通道，每个通道代表一个会话任务
            channel = connection.createChannel();
//声明交换机 String exchange, BuiltinExchangeType type
// /*** 参数明细
// * 1、交换机名称
// * 2、交换机类型，fanout、topic、direct、headers */
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);
            //声明队列
/*** 参数明细： * 1、队列名称 * 2、是否持久化 * 3、是否独占此队列 * 4、队列不用是否自动删除 * 5、参数 */
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
//交换机和队列绑定String queue, String exchange, String routingKey
// /*** 参数明细 * 1、队列名称 * 2、交换机名称 * 3、路由key */
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_FANOUT_INFORM,"");
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_FANOUT_INFORM,"");
//发送消息
            for (int i=0;i<10;i++){ String message = "inform to user"+i;
                /*** 参数明细
                 * * 1、交换机名称，不指令使用默认交换机名称 Default Exchange
                 * * 2、routingKey（路由key），根据key名称将消息转发到具体的队列，
                 * 这里填写队列名称表示消 息将发到此队列 * 3、消息属性 * 4、消息内容 */
                channel.basicPublish(EXCHANGE_FANOUT_INFORM, "", null, message.getBytes());
                System.out.println("Send Message is:'" + message + "'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally{
            if(channel!=null){
                try {channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace(); }
            }if(connection!=null){
                try {connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        }

}
