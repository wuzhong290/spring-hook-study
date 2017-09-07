package com.demo.designMode.strategy;

import org.springframework.stereotype.Component;

/**
 * Created by wuzhong on 2017/9/7.
 */
@Component(value="b")
public class TestBStrategy implements TestStrategy  {
    @Override
    public void action(StrategyContext context) {
        System.out.println("b");
    }
}
