package com.demo.lifecycle.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by wuzhong on 2017/6/2.
 */
public class LifeCycleBean implements InitializingBean,DisposableBean,BeanFactoryAware,BeanNameAware{

    private static final Logger LOG = LoggerFactory.getLogger(LifeCycleBean.class);

    private String desc;

    private String name;

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        LOG.info("setBeanFactory");
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        LOG.info("setBeanName");
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

    public String getDesc() {
        LOG.info("getDesc");
        return desc;
    }

    public void setDesc(String desc) {
        LOG.info("setDesc");
        this.desc = desc;
    }
}
