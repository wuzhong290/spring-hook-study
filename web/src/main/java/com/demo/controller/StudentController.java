package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import com.demo.service.StudentManager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/student-module")
public class StudentController {
    @Autowired
    StudentManager manager;

    @RequestMapping(value="/uc",method = RequestMethod.GET)
    public String quer(Model model,HttpServletResponse httpServletResponse) throws Exception {
        Cookie cookie = new Cookie("majun", "xiaoya");
        //设置cookie的过期时间是1000s
        cookie.setMaxAge(1000);
        httpServletResponse.addCookie(cookie);
        return "forward:/student-module/getStudentInfo";
    }
 
    @RequestMapping(value = "/getStudentInfo", method = RequestMethod.GET)
    public String getStudentInfo(Model model) {
        model.addAttribute("students", manager.getAllStudents());
        return "showStudentInfo";
    }

}
