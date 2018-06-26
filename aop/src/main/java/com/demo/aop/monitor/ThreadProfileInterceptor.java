package com.demo.aop.monitor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  所有的工程都会加载这个
 *  服务的interceptor，用来做慢的服务调用统计
 */
public class ThreadProfileInterceptor implements HandlerInterceptor, ApplicationEventPublisherAware {


    private static final Logger LOG = LoggerFactory.getLogger(ThreadProfileInterceptor.class);
    //多少毫秒会打印异常日志
    private  int threshold = 0;


    public final static String HTTP_HEADER_SERVICE_NAME = "X-YH-SERVICE-NAME";

    //服务的方法名
    private final static ThreadLocal<String> serviceNameThreadLocal = new ThreadLocal<>();

    //本服务内的方法名称
    private final static ThreadLocal<String> localServiceNameThreadLocal = new ThreadLocal<>();

    //publisher
    private ApplicationEventPublisher publisher;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOG.info("preHandle");
        //设置服务名称
        this.setupServiceName(request);

        //需要记录请求处理时长以及堆栈
        ThreadProfile.start(request.getRequestURI(), this.threshold);

        ThreadProfile.enter(this.getClass().getName(),"preHandle");

        return true;
    }


    private void setupServiceName(final HttpServletRequest request){
        String serviceName = request.getHeader(HTTP_HEADER_SERVICE_NAME);
        serviceNameThreadLocal.set(serviceName);
        localServiceNameThreadLocal.set(request.getRequestURI());
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOG.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOG.info("afterCompletion");
        /**remove all threadlocal */
        final String srcServiceName =  ThreadProfileInterceptor.getServiceName();
        serviceNameThreadLocal.remove();
        localServiceNameThreadLocal.remove();
        ThreadProfile.exit();
        /** do stat */
        Pair<String, Long> duration = ThreadProfile.stop();
        if(duration != null) {
            //服务调用的统计
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }


    /**
     * 获取服务名称
     * @return 服务名称
     */
    public static String  getServiceName(){
        String name= serviceNameThreadLocal.get();
        return StringUtils.isEmpty(name) ? "unknown" : name;
    }

    /**
     * 获取本地服务名称
     * @return 服务名称
     */
    public static String  getLocalServiceName(){
        String name= localServiceNameThreadLocal.get();
        return StringUtils.isEmpty(name) ? "unknown" : name;
    }
}
