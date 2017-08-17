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
    public String readValue() {
        String value1 = childService.getName("TaskServiceImpl", true);
        String value2 = value1 + "111";
        System.out.println("TaskServiceImpl getValue:"+value2);
        return value2;
    }
}
