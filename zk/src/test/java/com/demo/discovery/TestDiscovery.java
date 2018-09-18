package com.demo.discovery;

import com.demo.discovery.client.finder.ServiceFinder;
import com.demo.discovery.server.QGServiceDiscovery;
import com.demo.discovery.server.model.InstanceDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-zk.xml"})
public class TestDiscovery {
    @Autowired
    private ServiceFinder serviceFinder;

    @Test
    public void testFinder(){
        InstanceDetails instanceDetails = serviceFinder.getService("test.zk");
        System.out.println("");
    }
}
