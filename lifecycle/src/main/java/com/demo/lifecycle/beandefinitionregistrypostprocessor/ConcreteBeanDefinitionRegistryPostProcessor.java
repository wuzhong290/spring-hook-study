package com.demo.lifecycle.beandefinitionregistrypostprocessor;

import com.demo.lifecycle.bean.ConcreteRPBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author wuzhong
 */
@Component
public class ConcreteBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final String beanName = "concreteRPBean";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("=======postProcessBeanDefinitionRegistry");
        BeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(ConcreteRPBean.class)
                .getBeanDefinition();
        registry.registerBeanDefinition(beanName, beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("=======postProcessBeanFactory");
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
        MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue("author", "throwable");
    }
}
