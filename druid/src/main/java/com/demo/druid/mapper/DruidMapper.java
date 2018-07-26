package com.demo.druid.mapper;

import com.demo.druid.model.Country;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DruidMapper {
    Country selectById(int id);

    int updateCountrynameById(@Param("countryname") String countryname, @Param("id")int id);
    List<Country> selectAll();
}
