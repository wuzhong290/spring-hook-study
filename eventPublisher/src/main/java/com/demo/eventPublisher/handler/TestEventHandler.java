package com.demo.eventPublisher.handler;

import com.demo.eventPublisher.event.TestEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by wuzhong on 2017/9/12.
 */
@Component
@Slf4j(topic = "testEvent")
public class TestEventHandler {

    @Async
    @EventListener
    public void handleOrderCancelFinishedEvent(TestEvent event) {
        log.info("test event {}", event);
    }

}
