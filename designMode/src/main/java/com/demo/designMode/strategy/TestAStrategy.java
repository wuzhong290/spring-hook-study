package com.demo.designMode.strategy;

import org.springframework.stereotype.Component;

/**
 * Created by wuzhong on 2017/9/7.
 */
@Component(value="a")
public class TestAStrategy implements TestStrategy {
    @Override
    public void action(StrategyContext context) {
        System.out.println("a");
    }
}
