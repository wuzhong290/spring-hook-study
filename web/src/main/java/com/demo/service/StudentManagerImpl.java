package com.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.dao.StudentDao;
import com.demo.model.Student;
import com.demo.service.StudentManager;

@Service
public class StudentManagerImpl implements StudentManager {
    @Autowired
    StudentDao dao;
     
    public List<Student> getAllStudents() {
        return dao.getAllStudents();
    }
}
