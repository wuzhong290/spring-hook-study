package com.demo.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface HelloService {

	@WebMethod(action = "say-hello")
	String sayHello(@WebParam(name = "name") String name);
}
