package com.demo.rxjava.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by wuzhong on 2017/9/19.
 */
public class EventDto {

    private final String name;

    private final Date date;

    @JsonCreator
    public EventDto(@JsonProperty("name") String name, @JsonProperty("date") Date date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }
}