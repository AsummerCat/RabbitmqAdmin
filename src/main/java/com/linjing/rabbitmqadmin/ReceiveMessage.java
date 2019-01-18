package com.linjing.rabbitmqadmin;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author cxc
 * @date 2019/1/18 09:42
 */
@Component
public class ReceiveMessage {
    /**
     * 监听单个队列
     */
    @RabbitListener(queues = "TTTTT")
    public void alternateQueueProcess(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        System.out.println("--0--客户端接收到请求");
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("receive: " + new String(message.getBody()));
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            System.out.println("receiver fail");
        }
    }

    @RabbitListener(queues = "TTTTT")
    public void alternateQueueProcess1(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        System.out.println("--1--客户端接收到请求");
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("receive: " + new String(message.getBody()));
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            System.out.println("receiver fail");
        }
    }

    @RabbitListener(queues = "TTTTT")
    public void alternateQueueProcess2(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        System.out.println("--2--客户端接收到请求");
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("receive: " + new String(message.getBody()));
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            System.out.println("receiver fail");
        }
    }

    @RabbitListener(queues = "TTTTT")
    public void alternateQueueProcess3(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        System.out.println("--3--客户端接收到请求");
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("receive: " + new String(message.getBody()));
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            System.out.println("receiver fail");
        }
    }

    @RabbitListener(queues = "TTTTT")
    public void alternateQueueProcess4(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        System.out.println("--4--客户端接收到请求");
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("receive: " + new String(message.getBody()));
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            System.out.println("receiver fail");
        }
    }

    @RabbitListener(queues = "TTTTT")
    public void alternateQueueProcess5(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        System.out.println("--5--客户端接收到请求");
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println("receive: " + new String(message.getBody()));
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            System.out.println("receiver fail");
        }
    }
}
