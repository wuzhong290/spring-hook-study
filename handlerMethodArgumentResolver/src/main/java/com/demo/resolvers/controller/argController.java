package com.demo.resolvers.controller;

import com.demo.converters.model.Person;
import com.demo.resolvers.annotation.Json;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wuzhong on 2017/5/31.
 */
@Controller
@RequestMapping("/arg")
public class argController {

    @RequestMapping(value = "/person")
    @ResponseBody
    public Person getStudentInfo(@Json Person person) {
        return person;
    }
}
