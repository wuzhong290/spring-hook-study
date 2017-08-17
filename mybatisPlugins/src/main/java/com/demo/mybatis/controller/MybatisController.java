package com.demo.mybatis.controller;

import com.demo.mybatis.service.DemoService;
import com.demo.mybatis.model.Country;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author liuzh
 */
@Controller
@RequestMapping("/demo")
public class MybatisController {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = {"/", "index.html"})
    public String index() {
        return "index";
    }

    @ResponseBody
    @RequestMapping("test1")
    public Country requestTest5(HttpEntity<String> message) {
        return demoService.selectById(35);
    }

    @RequestMapping("test2")
    public ResponseEntity<Country> requestTest6() {
        return new ResponseEntity<Country>(demoService.selectById2(35), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/test3", method = RequestMethod.GET)
    public List<Country> requestTest7(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        return demoService.selectPage(pageNum, pageSize);
    }

    public static void main(String[] args) {
        System.out.println(SqlSessionFactoryBean.class.getSimpleName());
    }
}
