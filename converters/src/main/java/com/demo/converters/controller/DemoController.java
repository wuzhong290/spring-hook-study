package com.demo.converters.controller;

import com.demo.converters.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by thinkpad on 2017/5/24.
 * url:http://localhost:8080/demo/person
 * 入参：ewoibmFtZSI6ImRkZGRkZGRkZGRkZGQiLAoiYWdlIjoxMTEKfQ==
 * 出参：{
 "age": 111,
 "name": "ddddddddddddd"
 }
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping(value = "/person")
    @ResponseBody
    public Person getStudentInfo(@RequestBody Person person) {
        return person;
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test(Date birthday){
        System.out.println(birthday);
        return "index";
    }
}
