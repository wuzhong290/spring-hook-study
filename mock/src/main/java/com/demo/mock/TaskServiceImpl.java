package com.demo.mock;

import org.springframework.stereotype.Service;

/**
 * Created by wuzhong on 2017/8/15.
 */
@Service
public class TaskServiceImpl extends TaskService {


    @Override
    public String readValue() {
        String value1 = childService.getName("TaskServiceImpl", true);
        String value2 = value1 + "111";
        System.out.println("TaskServiceImpl getValue:"+value2);
        return value2;
    }
}
