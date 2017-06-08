package com.demo.mybatis.plugins;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by wuzhong on 2017/6/8.
 */
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}),
        @Signature(type = ResultSetHandler.class, method = "handleOutputParameters", args = {CallableStatement.class})
})
public class ResultSetHandlerInterceptor  implements Interceptor {
    private static final Logger LOG = LoggerFactory.getLogger(ResultSetHandlerInterceptor.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        LOG.info("intercept:{},args={},target{}"+invocation.getMethod(),invocation.getArgs(),invocation.getTarget());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}