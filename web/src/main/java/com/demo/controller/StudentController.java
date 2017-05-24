package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import com.demo.service.StudentManager;

@Controller
@RequestMapping("/student-module")
public class StudentController {
    @Autowired
    StudentManager manager;
 
    @RequestMapping(value = "/getStudentInfo", method = RequestMethod.GET)
    public String getStudentInfo(Model model) {
        model.addAttribute("students", manager.getAllStudents());
        return "showStudentInfo";
    }

}
