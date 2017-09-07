package com.demo.designMode.service;

import com.alibaba.fastjson.JSON;
import com.demo.designMode.strategy.TestStrategy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by wuzhong on 2017/9/7.
 */
@Service
public class StrategyService {

    @Resource
    private Map<String,TestStrategy> strategyMap;

    public void test(){
        System.out.println(JSON.toJSONString(strategyMap));
    }
}
