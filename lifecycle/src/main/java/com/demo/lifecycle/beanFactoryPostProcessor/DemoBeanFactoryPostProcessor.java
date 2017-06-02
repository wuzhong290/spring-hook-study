package com.demo.lifecycle.beanFactoryPostProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by wuzhong on 2017/6/2.
 */
public class DemoBeanFactoryPostProcessor implements BeanFactoryPostProcessor,InitializingBean,DisposableBean,BeanFactoryAware,BeanNameAware {
    private static final Logger LOG = LoggerFactory.getLogger(DemoBeanFactoryPostProcessor.class);

    private String name;

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        LOG.info("setBeanFactory");
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        LOG.info("setBeanName:"+name);
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

    private void initMethod(){
        LOG.info("initMethod");
    }

    private void destroyMethod(){
        LOG.info("destroyMethod");
    }
    @PostConstruct
    private void postConstruct(){
        LOG.info("postConstruct");
    }
    @PreDestroy
    private void preDestroy(){
        LOG.info("preDestroy");
    }
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        LOG.info("postProcessBeanFactory");
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("lifeCycleBean");
        MutablePropertyValues pv =  beanDefinition.getPropertyValues();
        if(pv.contains("mes"))
        {
            pv.addPropertyValue("mes","message");
        }
    }
}
