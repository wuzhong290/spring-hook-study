package com.demo.druid;

import com.demo.druid.mapper.LogPageMapper;
import com.demo.druid.model.LogPage;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-druid.xml"})
public class TestDruid {
    @Autowired
    private LogPageMapper logPageMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void testMapper(){
        LogPage logPage = logPageMapper.selectByPrimaryKey(1568015);
        System.out.println(logPage.getId());
    }
    @Test
    public void testMapper1(){
        LogPageMapper mapper = sqlSessionFactory.openSession().getMapper(LogPageMapper.class);
        LogPage logPage = mapper.selectByPrimaryKey(1568015);
        System.out.println(logPage.getId());
    }
}
