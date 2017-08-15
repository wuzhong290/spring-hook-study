package com.demo.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wuzhong on 2017/8/15.
 */
@Service
public class ReportServiceImpl {

    @Autowired
    private TaskService taskServiceImpl;


    public ReportServiceImpl() {

    }


    public ReportServiceImpl(TaskService taskService) {
        this.taskServiceImpl = taskService;
    }


    public void setTaskServiceImpl(TaskService taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }


    public TaskService getTaskServiceImpl() {
        return taskServiceImpl;
    }
}
