package com.demo.rest.aop;

import com.demo.rest.annotation.RestTrack;
import com.demo.rest.service.RestTrackService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class RestTrackAop {

    private static final Logger LOG = LoggerFactory.getLogger(RestTrackAop.class);

    @Autowired
    private RestTrackService restTrackService;

    @Pointcut("@annotation(com.demo.rest.annotation.RestTrack)")
    public void pointcut(){}

    @Around(value="pointcut()")
    public Object aroundMethod(ProceedingJoinPoint jointPoint){
        Object[] args = jointPoint.getArgs();
        Method method = ((MethodSignature) jointPoint.getSignature()).getMethod();
        RestTrack restTrack = method.getAnnotation(RestTrack.class);
        String url = restTrack.url();
        Object result = null;
        try {
            result = jointPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            restTrackService.restTrack(url, args, result, 1);
            return result;
        }
        restTrackService.restTrack(url, args, result, 0);
        return result;
    }
}
