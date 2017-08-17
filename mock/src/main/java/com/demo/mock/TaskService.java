package com.demo.mock;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wuzhong on 2017/8/15.
 */
public abstract class TaskService {

    @Autowired
    ChildService childService;

    abstract String readValue();
}
