package com.demo.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-ssl-rest.xml"})
public class TestSslRest {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void getSign(){
        String res = restTemplate.getForObject("https://127.0.0.1/getSign", String.class);
        System.out.println(res);
    }
}
