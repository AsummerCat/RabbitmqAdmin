package com.linjing.rabbitmqadmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * RabbitMQ的配置文件
 *
 * @author cxc
 * @date 2019/1/17 17:47
 */
@Configuration
public class RabbitMQConfig {

    @Autowired
    private ConnectionFactory connectionFactory;


    /**
     * 管理工具
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        //设置自动开启 默认是ture
        rabbitAdmin.setAutoStartup(true);
        //创建交换机
        rabbitAdmin.declareExchange(new DirectExchange("test.direct", false, false));
        rabbitAdmin.declareExchange(new TopicExchange("topic.direct", false, false));
        rabbitAdmin.declareExchange(new FanoutExchange("fanout.direct", false, false));
        //创建队列
        rabbitAdmin.declareQueue(new Queue("test.direct.queue", false));
        rabbitAdmin.declareQueue(new Queue("test.topic.queue", false));
        rabbitAdmin.declareQueue(new Queue("test.fanout.queue", false));

        //绑定 ->交换机->路由
        rabbitAdmin.declareBinding(new Binding("test.direct.queue",
                Binding.DestinationType.QUEUE, "test.direct", "direct", new HashMap<>()));

        //绑定 ->交换机 ->指定的路由key
        rabbitAdmin.declareBinding(
                BindingBuilder.bind(new Queue("test.topic.queue", false))        //直接创建队列
                        .to(new TopicExchange("topic.direct", false, false))    //直接创建交换机 建立关联关系
                        .with("user.#"));    //指定路由Key

        //绑定 ->交换机
        rabbitAdmin.declareBinding(
                BindingBuilder
                        .bind(new Queue("test.fanout.queue", false))
                        .to(new FanoutExchange("fanout.direct", false, false)));

        //进行解绑
        //rabbitAdmin.removeBinding(BindingBuilder.bind(new Queue("info")).
        //        to(new TopicExchange("direct.exchange")).with("key.2"));
        //
        ////exchange与exchange绑定
        //rabbitAdmin.declareBinding(new Binding("exchange1", Binding.DestinationType.EXCHANGE,
        //        "exchange2", "key.4", new HashMap()));


        //清空队列数据 后面参数是是否异步处理
        rabbitAdmin.purgeQueue("test.topic.queue", false);


        // 删除交换器
        rabbitAdmin.deleteExchange("ArgumentsExchange");
        rabbitAdmin.deleteExchange("alternateExchange");

        //删除队列
        rabbitAdmin.deleteQueue("hello");
        rabbitAdmin.deleteQueue("alternateQueue");
        rabbitAdmin.deleteQueue("basic_queue");
        rabbitAdmin.deleteQueue("dieQueue");




        return rabbitAdmin;
    }


    /** ======================== 定制一些处理策略 =============================*/

    /**
     * 定制化amqp模版 发布确认模式
     * <p>
     * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调   即消息发送到exchange  ack
     * ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调  即消息发送不到任何一个队列中  ack
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        Logger log = LoggerFactory.getLogger(RabbitTemplate.class);
        RabbitTemplate rabbitTemp = new RabbitTemplate(connectionFactory);
        // 消息发送失败返回到队列中, yml需要配置 publisher-returns: true
        rabbitTemp.setMandatory(true);

        // 消息返回, yml需要配置 publisher-returns: true
        rabbitTemp.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().toString();
            log.info("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        });

        // 消息确认, yml需要配置 publisher-confirms: true
        rabbitTemp.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息发送到exchange成功,id: {}", correlationData.getId());
            } else {
                log.info("消息发送到exchange失败,原因: {}", cause);
            }
        });
        return rabbitTemp;
    }

}
