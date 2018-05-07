package com.demo.lifecycle.beandefinitionregistrypostprocessor;

import com.demo.lifecycle.bean.ConcreteRPBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-bean-test.xml"
})
public class ConcreteRPBeanTest {

    @Autowired
    private ConcreteRPBean concreteRPBean;

    @Test
    public void sayHello() throws Exception {
        concreteRPBean.sayHello();
    }

}
