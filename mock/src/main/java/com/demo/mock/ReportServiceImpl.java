package com.demo.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wuzhong on 2017/8/15.
 */
@Service
public class ReportServiceImpl {

    @Resource(name = "taskServiceImpl")
    private TaskServiceImpl taskServiceImpl;


    public ReportServiceImpl() {

    }


    public ReportServiceImpl(TaskServiceImpl taskService) {
        this.taskServiceImpl = taskService;
    }


    public void setTaskServiceImpl(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }


    public TaskService getTaskServiceImpl() {
        return taskServiceImpl;
    }
}
