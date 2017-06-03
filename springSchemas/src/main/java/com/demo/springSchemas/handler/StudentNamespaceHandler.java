package com.demo.springSchemas.handler;


import com.demo.springSchemas.parser.StudentBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class StudentNamespaceHandler  extends NamespaceHandlerSupport {

    public void init() {
        registerBeanDefinitionParser("student", new StudentBeanDefinitionParser());
    }

}
