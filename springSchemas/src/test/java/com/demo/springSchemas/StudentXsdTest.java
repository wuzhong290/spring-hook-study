package com.demo.springSchemas;

import com.demo.springSchemas.model.Student;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:applicationContext-*.xml"})
public class StudentXsdTest {
    @Autowired
    ApplicationContext ctx;

    @Test
    public void test(){
        Student student1 = (Student) ctx.getBean("student1");
        Student student2 = (Student) ctx.getBean("student2");
        Assert.assertEquals("student1", student1.getName());
        Assert.assertEquals(18, student1.getAge());

        Assert.assertEquals("student2", student2.getName());
        Assert.assertEquals(20, student2.getAge());
    }
}
