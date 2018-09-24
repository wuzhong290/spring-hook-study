package com.demo.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-rest.xml"})
public class TestRest {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testHttps(){
        Map m = restTemplate.getForObject("https://localhost/getSign?AccessKey=access&home=world&name=hello&work=java&timestamp=now&nonce=random", Map.class);
        System.out.println("");
    }
}
