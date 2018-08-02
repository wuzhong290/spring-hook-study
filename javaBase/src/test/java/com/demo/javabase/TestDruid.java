package com.demo.javabase;

import com.demo.druid.mapper.RandomNumMapper;
import com.demo.druid.model.RandomNum;
import com.demo.javabase.random.RandomCallback;
import com.demo.javabase.random.RandomCreate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-druid.xml"})
public class TestDruid {

    @Autowired
    private RandomNumMapper randomNumMapper;
    @Test
    public void testRandomCreate(){
        RandomCreate randomCreate = new RandomCreate.Builder()
                .randomLen(6)
                .index(0)
                .callback(new RandomCallback() {
                    @Override
                    public void doCallBack(String random, int index) {
                        RandomNum randomNum =  new RandomNum();
                        randomNum.setRandomNum(Integer.valueOf(random));
                        randomNumMapper.insertRandomNum(randomNum);
                    }
                })
                .build();
        randomCreate.createRandom("",1,1);
    }
}
