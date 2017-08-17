package com.demo.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wuzhong on 2017/8/15.
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private ChildService childService;

    @Override
    public String getValue() {
        System.out.println("TaskServiceImpl getValue");
        return childService.getName();
    }
}
