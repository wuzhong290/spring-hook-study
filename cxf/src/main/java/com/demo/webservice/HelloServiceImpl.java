package com.demo.webservice;

import javax.jws.WebService;

@WebService(endpointInterface = "com.demo.webservice.HelloService")
public class HelloServiceImpl implements HelloService {

    public String sayHello(String name) {
 		return "Hello " + name;
    }
}
