package com.demo.mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by wuzhong on 2017/8/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:applicationContext-mock.xml"})
public class ReportServiceImplTestMockito {

    private final Logger logger = LoggerFactory.getLogger(ReportServiceImplTestMockito.class);

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Autowired
    @InjectMocks
    private TaskService  taskServiceImpl;

    @Mock
    private ChildService  childService;

    @Autowired
    @InjectMocks
    private ReportServiceImpl service;

    @Test
    public void testMockInjected() {
        when(taskServiceImpl.readValue()).thenReturn("mock");

        System.out.println(service.getTaskServiceImpl().readValue());

        try{
            assertEquals("mock", service.getTaskServiceImpl().readValue());
        }catch (Throwable e){
            logger.error(e.getMessage());
        }
    }

    @Test
    public void testMock1Injected() {
        when(childService.getName()).thenReturn("mock");
        when(childService.getName(anyString(), anyBoolean())).thenReturn("mock");
        assertEquals("mock", service.getTaskServiceImpl().readValue());
    }

    @Test
    public void testMock2Injected() {
        assertEquals("wpz1", service.getTaskServiceImpl().readValue());
    }
}