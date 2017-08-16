package com.demo.mock;

import org.springframework.stereotype.Service;

/**
 * Created by wuzhong on 2017/8/15.
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Override
    public String getValue() {
        return "dddddddddddddddddddddddddd";
    }
}
