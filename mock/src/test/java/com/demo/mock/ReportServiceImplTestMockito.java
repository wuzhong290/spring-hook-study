package com.demo.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by wuzhong on 2017/8/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:applicationContext-mock.xml"})
public class ReportServiceImplTestMockito {

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private TaskService  taskServiceImpl;


    @Autowired
    @InjectMocks
    private ReportServiceImpl service;


    @Test
    public void testMockInjected() {
        System.out.println(service.getTaskServiceImpl().getValue());
    }
}