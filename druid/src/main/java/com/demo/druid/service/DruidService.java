package com.demo.druid.service;

import com.demo.druid.mapper.DruidMapper;
import com.demo.druid.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author liuzh
 */
@Service
public class DruidService {
    @Autowired
    private DruidMapper druidMapper;

    @Resource(name = "jdbcTemplate_druid")
    private JdbcTemplate jdbcTemplateDruid;

    public Country selectById(int id) {
        return druidMapper.selectById(id);
    }

    public Country selectById2(int id) {
        Country country = jdbcTemplateDruid.queryForObject("select * from country where id = :id", new BeanPropertyRowMapper<Country>(Country.class), id);
        return country;
    }

    public int updateCountrynameById(String countryname, int id) {
        return  druidMapper.updateCountrynameById(countryname, id);
    }
}
