package com.linjing.rabbitmqadmin;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author cxc
 * @date 2019/1/18 09:35
 */
@RestController
public class AdminController {

    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("testAdmin")
    public String testAdmin() {
        rabbitAdmin.deleteExchange("test.direct");
        rabbitAdmin.deleteExchange("topic.direct");
        rabbitAdmin.deleteExchange("fanout.direct");
        rabbitAdmin.deleteExchange("topic.exchange");
        rabbitAdmin.deleteExchange("timeoutExchange");
        rabbitAdmin.deleteExchange("direct_exchange");
        rabbitAdmin.deleteExchange("dieExchange");

        rabbitAdmin.deleteQueue("test.direct.queue");
        rabbitAdmin.deleteQueue("test.topic.queue");
        rabbitAdmin.deleteQueue("test.fanout.queue");
        rabbitAdmin.deleteQueue("ArgumentsQueue");
        rabbitAdmin.deleteQueue("firstQueue");
        rabbitAdmin.deleteQueue("timeoutQueue");
        rabbitAdmin.deleteQueue(" topic.queue1");
        rabbitAdmin.deleteQueue(" topic.queue2");


        rabbitAdmin.declareQueue(new Queue("TTTTT"));
        //清空队列数据 后面参数是是否异步处理
        rabbitAdmin.purgeQueue("TTTTT", true);
        return "测试成功";
    }


    @RequestMapping("testRabbitMQ")
    @Async
    public String testRabbitMQ() {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        String text = "嘿嘿嘿嘿嘿";
        for (; ; ) {
            rabbitTemplate.convertAndSend("TTTTT", (Object) text, correlationData);

        }
    }
}
