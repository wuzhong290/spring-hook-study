package com.demo.converters.model;

import com.demo.converters.annotation.JsonDemo;

/**
 * Created by thinkpad on 2017/5/24.
 */
@JsonDemo
public class Person {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
