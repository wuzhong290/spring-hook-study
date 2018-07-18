package com.demo.druid.controller;

import com.demo.druid.model.Country;
import com.demo.druid.service.DruidService;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liuzh
 */
@Controller
@RequestMapping("/db")
public class DruidController {

    @Autowired
    private DruidService druidService;

    @RequestMapping(value = {"/", "index.html"})
    public String index() {
        return "index";
    }

    @ResponseBody
    @RequestMapping("test1")
    public Country requestTest5(HttpEntity<String> message) {
        return druidService.selectById(35);
    }

    @RequestMapping("test2")
    public ResponseEntity<Country> requestTest6() {
        return new ResponseEntity<Country>(druidService.selectById2(35), HttpStatus.OK);
    }

    public static void main(String[] args) {
        System.out.println(SqlSessionFactoryBean.class.getSimpleName());
    }
}
