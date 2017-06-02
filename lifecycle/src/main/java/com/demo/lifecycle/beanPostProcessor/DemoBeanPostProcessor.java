package com.demo.lifecycle.beanPostProcessor;

import com.demo.lifecycle.bean.LifeCycleBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by wuzhong on 2017/6/2.
 */
public class DemoBeanPostProcessor implements BeanPostProcessor,InitializingBean,DisposableBean,BeanFactoryAware,BeanNameAware {
    private static final Logger LOG = LoggerFactory.getLogger(DemoBeanPostProcessor.class);

    private String name;

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        LOG.info("setBeanFactory");
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        LOG.info("setBeanName::"+name);
        this.name = name;
    }

    @Override
    public void destroy() throws Exception {
        LOG.info("destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("afterPropertiesSet");
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof LifeCycleBean){
            LOG.info("postProcessBeforeInitialization:"+bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof LifeCycleBean){
            LOG.info("postProcessAfterInitialization:"+bean);
        }
        return bean;
    }
}
