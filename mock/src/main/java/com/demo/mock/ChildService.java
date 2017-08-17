package com.demo.mock;

import org.springframework.stereotype.Service;

/**
 * Created by wuzhong on 2017/8/17.
 */
@Service
public class ChildService {

    String getName(){
        return  "wpz";
    }

    String getName(String name, boolean flag){
        return  name + flag;
    }
}
