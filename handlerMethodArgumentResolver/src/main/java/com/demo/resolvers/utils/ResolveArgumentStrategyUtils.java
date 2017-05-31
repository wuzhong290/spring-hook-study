package com.demo.resolvers.utils;

import com.demo.resolvers.strategy.DefaultArgumentStrategy;
import com.demo.resolvers.strategy.GetArgumentStrategy;
import com.demo.resolvers.strategy.ResolveArgumentStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuzhong on 2017/5/31.
 */
public class ResolveArgumentStrategyUtils {
    private final static Map<String, ResolveArgumentStrategy> STRATEGYS = new HashMap<>();

    static{
        ResolveArgumentStrategy getArgumentStrategy = new GetArgumentStrategy();
        ResolveArgumentStrategy defaultArgumentStrategy = new DefaultArgumentStrategy();
        STRATEGYS.put("GET", getArgumentStrategy);
        STRATEGYS.put("DELETE", getArgumentStrategy);
        STRATEGYS.put("POST", defaultArgumentStrategy);
        STRATEGYS.put("DEFAULT", defaultArgumentStrategy);
    }

    public static ResolveArgumentStrategy getResolveArgumentStrategy(String key){
        return STRATEGYS.get(key);
    }
}
