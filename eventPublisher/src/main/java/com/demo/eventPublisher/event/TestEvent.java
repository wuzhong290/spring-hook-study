package com.demo.eventPublisher.event;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Builder;

/**
 * Created by wuzhong on 2017/9/12.
 */
@Builder
@Data
@ToString
public class TestEvent {
    private String eventName;
    private long eventTime;
}
