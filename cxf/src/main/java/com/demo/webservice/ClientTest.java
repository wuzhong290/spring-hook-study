package com.demo.webservice;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import java.net.URL;

/**
 * Created by wuzhong on 2017/8/9.
 */
public class ClientTest {

    public static void main(String[] args) {

        try {

            URL location = new URL("http://127.0.0.1:8081/cxf/HelloService?wsdl");
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory
                    .newInstance();
            if (dcf != null) {
                dcf.setSimpleBindingEnabled(true);
                Client client = dcf.createClient(location);
                Object[] res = client.invoke("sayHello", "ddd");
                System.out.println("Dynamic Method response : " + res[0]);
            } else {
                System.out
                        .println("Unable to create a dynamic factory instance");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//        factory.setServiceClass(HelloService.class);
//        factory.setAddress("http://localhost:8080/HelloService");
//
//        HelloService service = (HelloService) factory.create();
//        System.out.println(service.sayHello("ddd"));
    }
}
