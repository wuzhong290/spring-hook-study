package com.demo.druid.mapper;

import com.demo.druid.model.Country;

import java.util.List;

public interface DruidMapper {
    Country selectById(int id);
    List<Country> selectAll();
}
