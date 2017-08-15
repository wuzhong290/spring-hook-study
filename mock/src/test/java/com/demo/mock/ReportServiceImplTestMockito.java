package com.demo.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by wuzhong on 2017/8/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReportServiceImplTestMockito {

    @Mock
    private TaskService  mockTaskService;

    @InjectMocks
    private ReportServiceImpl service;


    @Test
    public void testMockInjected() {
        System.out.println(service.getTaskServiceImpl());
    }
}