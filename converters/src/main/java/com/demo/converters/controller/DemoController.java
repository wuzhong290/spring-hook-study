package com.demo.converters.controller;

import com.demo.converters.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by thinkpad on 2017/5/24.
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping(value = "/person")
    @ResponseBody
    public  String getStudentInfo(@RequestBody Person person) {
        return person.getName();
    }
}
