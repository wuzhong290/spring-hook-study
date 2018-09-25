package com.demo.message;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 发送消息到Message broker中 当前是发送到topic中
 */
public class QGProducerTemplate {

    private final Logger logger = LoggerFactory.getLogger(QGProducerTemplate.class);

    private final Logger asynclogger = LoggerFactory.getLogger("asyncproducer");

    private final int DEFAULT_QUEUE_SIZE = 8000;

    private final LinkedBlockingQueue<Entry> asyncSendQueues = new LinkedBlockingQueue<>(DEFAULT_QUEUE_SIZE);

    //针对单个bean的开关,可以在配置文件里面注入,默认true
    private boolean asyncEnable = true;

    private AmqpTemplate amqpTemplate;

    private final String TOPIC_EXCHAGE = "amq.topic";

    {
        new Thread("ProducerTemplate-thread") {
            @Override
            public void run() {
                try {
                    Entry e;
                    while ((e = asyncSendQueues.take()) != null) {
                        try {
                            amqpTemplate.send(e.topicExchange, e.topic, e.amqpMsg);
                        } catch (Exception exception) {
                            asynclogger.error("mq send exception,{}", exception);
                        }
                    }
                } catch (Exception e) {
                    asynclogger.error("ProducerTemplate-thread error ,{}", e);
                }
            }
        }.start();
    }

    public boolean isAsyncEnable() {
		return asyncEnable;
	}

	public void setAsyncEnable(boolean asyncEnable) {
		this.asyncEnable = asyncEnable;
	}

	/**
     * 延迟发送消息
     *
     * @param topic          消息的主题
     * @param object         消息对象，框架会自动转化为JSON
     * @param attributes     消息属性
     * @param delayInMinutes 延迟多少分钟，必须和consumers配置的保持一致
     */
    public void send(String topic, Object object, Map<String, Object> attributes, int delayInMinutes) {
        //yoho_delay.2m.topic
        String sent_topic = "qg_delay." + delayInMinutes + "m." + topic;
        this.send(sent_topic, object, attributes);
    }

    /**
     * 发送消息
     * @param topic  消息主题
     * @param object 消息体，java对象
     */
    public void send(String topic, Object object) {
        this.send(topic, object, null);
    }

    /**
     * 发送消息
     * @param topic      消息主题
     * @param object     消息体 会转化为JSON，然后发送
     * @param attributes 消息属性
     */
    public void send(String topic, Object object, Map<String, Object> attributes) {
        //消息的属性
        MessageProperties properties = new MessageProperties();
        properties.setContentType("text");
        if (attributes != null) {
            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                properties.setHeader(entry.getKey(), entry.getValue());
            }
        }
        //消息体
        byte[] body = JSON.toJSONString(object).getBytes(Charsets.toCharset("UTF-8"));
        Message amqpMsg = new Message(body, properties);

        if (asyncEnable) {
            asycnSend(TOPIC_EXCHAGE, topic, amqpMsg);
        } else {
            this.amqpTemplate.send(TOPIC_EXCHAGE, topic, amqpMsg);
        }
        //发送事件
        logger.debug("send mq message success. exchange:{}, topic:{}, message:{}", TOPIC_EXCHAGE, topic, object);
    }

    //spring setting, do not call
    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    private void asycnSend(String topicExchange, String topic, Message amqpMsg) {
        if (!asyncSendQueues.offer(new Entry(topicExchange, topic, amqpMsg))) {
            try {
                asynclogger.error("mq queue is full ,and topicExchange={} ,topic={},amqpMsg={}", topicExchange, topic, new String(amqpMsg.getBody(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                asynclogger.error(e.getMessage());
            }
        }
    }

    private class Entry {
        private String topicExchange;
        private String topic;
        private Message amqpMsg;

        public Entry(String topicExchange, String topic, Message amqpMsg) {
            this.amqpMsg = amqpMsg;
            this.topic = topic;
            this.topicExchange = topicExchange;
        }
    }
}
