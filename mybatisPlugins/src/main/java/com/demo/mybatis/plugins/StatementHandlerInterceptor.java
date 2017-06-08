package com.demo.mybatis.plugins;


import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by wuzhong on 2017/6/8.
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class}),
        @Signature(type = StatementHandler.class, method = "parameterize", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "getParameterHandler", args = {}),
        @Signature(type = StatementHandler.class, method = "getBoundSql", args = {}),
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class})
})
public class StatementHandlerInterceptor implements Interceptor {
    private static final Logger LOG = LoggerFactory.getLogger(StatementHandlerInterceptor.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        LOG.info("intercept:{},args={},target{}"+invocation.getMethod(),invocation.getArgs(),invocation.getTarget());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
