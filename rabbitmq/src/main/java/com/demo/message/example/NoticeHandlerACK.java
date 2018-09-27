package com.demo.message.example;

import com.demo.message.QGMessageConsumer;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class NoticeHandlerACK implements ChannelAwareMessageListener, QGMessageConsumer {
    private final Logger logger = LoggerFactory.getLogger(NoticeHandlerACK.class);
    @Override
    public String getMessageTopic() {
        return "notice_test";
    }

    @Override
    public void handleMessage(Object message) {
        logger.info("消费确认："+message.toString());
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        logger.info("消费手动确认："+message.toString());
        logger.info("消费手动确认deliveryTag："+message.getMessageProperties().getDeliveryTag());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
