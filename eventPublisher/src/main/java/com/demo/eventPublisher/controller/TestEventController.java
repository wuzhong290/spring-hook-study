package com.demo.eventPublisher.controller;

import com.demo.eventPublisher.event.TestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wuzhong on 2017/9/12.
 */
@Controller
@RequestMapping("/event")
public class TestEventController {

    @Autowired
    private ApplicationEventPublisher publisher;


    @RequestMapping(value = "/event")
    @ResponseBody
    public void event(){
        TestEvent event = TestEvent.builder().eventName("event").eventTime(System.currentTimeMillis()).build();
        publisher.publishEvent(event);
    }
}
