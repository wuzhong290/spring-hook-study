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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.when;

/**
 * Created by thinkpad on 2017/8/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:applicationContext-mock.xml"})
public class MockTest1 {
    @Autowired
    @InjectMocks
    private TaskServiceImpl  taskServiceImpl;

    @Mock
    private ChildService  childService;

    @Autowired
    @InjectMocks
    private ReportServiceImpl service;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMockWhen() {
        when(childService.getName()).thenReturn("mock1mock2");
        when(childService.getName(anyString(), anyBoolean())).thenReturn("mock1mock2");
        assertEquals("mock1mock2", childService.getName());
        assertEquals("mock1mock2", childService.getName("1", true));
    }

    @Test
    public void testMockWhen1() {
        when(childService.getName()).thenReturn("mock1mock2");
        when(childService.getName(anyString(), anyBoolean())).thenReturn("mock1mock2");
        assertEquals("mock1mock2111", taskServiceImpl.readValue());
    }

    @Test
    public void testMockWhen3() {
        when(childService.getName()).thenReturn("mock1mock2");
        when(childService.getName(anyString(), anyBoolean())).thenReturn("mock1mock2");
        assertEquals("mock1mock2111", service.getTaskServiceImpl().readValue());
    }
}
