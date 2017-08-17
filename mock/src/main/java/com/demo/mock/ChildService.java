package com.demo.mock;

import org.springframework.stereotype.Service;

/**
 * Created by wuzhong on 2017/8/17.
 */
@Service
public class ChildService {

    String getName(){
        System.out.println("ChildService getName:");
        return  "wpz";
    }

    String getName(String name, boolean flag){
        System.out.println("ChildService getName:"+ name + flag);
        return  name + flag;
    }
}
