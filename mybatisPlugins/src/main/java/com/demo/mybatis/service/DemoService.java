package com.demo.mybatis.service;

import com.demo.mybatis.mapper.CountryMapper;
import com.demo.mybatis.mapper.UserInfoMapper;
import com.demo.mybatis.mapper.UserLoginMapper;
import com.demo.mybatis.model.Country;
import com.demo.mybatis.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author liuzh
 */
@Service
public class DemoService {
    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserLoginMapper userLoginMapper;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Country selectById(int id) {
        return countryMapper.selectByPrimaryKey(id);
    }

    public Country selectById2(int id) {
        Country country = jdbcTemplate.queryForObject("select * from country where id = :id", new BeanPropertyRowMapper<Country>(Country.class), id);
        return country;
    }

    public List<Country> selectPage(int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        return countryMapper.select(new Country());
    }
}
