
package com.njqg;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 3.1.0
 * 2018-01-05T21:34:54.537+08:00
 * Generated source version: 3.1.0
 * 
 */
 
public class HelloService_HelloServiceImplPort_Server{

    protected HelloService_HelloServiceImplPort_Server() throws Exception {
        System.out.println("Starting Server");
        Object implementor = new HelloServiceImplPortImpl();
        String address = "http://127.0.0.1:8080/cxf/HelloService";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws Exception {
        new HelloService_HelloServiceImplPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}