package com.demo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.model.Student;

@Repository
public class StudentDaoImpl implements StudentDao {
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<Student>();
         
        Student stu1 = new Student();
        stu1.setId(1);
        stu1.setName("Zhang San");
        students.add(stu1);
         
        Student stu2 = new Student();
        stu2.setId(2);
        stu2.setName("Li Si");
        students.add(stu2);
         
        return students;
    }
}
