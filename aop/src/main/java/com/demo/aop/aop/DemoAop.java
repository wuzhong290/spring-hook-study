package com.demo.aop.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by wuzhong on 2017/11/3.
 */
@Component
@Aspect
public class DemoAop {

    private static final Logger LOG = LoggerFactory.getLogger(DemoAop.class);

    @Pointcut("@annotation(TestAop)")
    public void pointcut(){}

    @Around(value="pointcut()")
    public Object aroundMethod(ProceedingJoinPoint jointPoint){
        LOG.info("begin...................");
        Object result = null;
        try {
            result = jointPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        LOG.info("end...................");
        return result;
    }
}
