package com.demo.archaius;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.config.DynamicWatchedConfiguration;
import com.netflix.config.source.ZooKeeperConfigurationSource;
import com.netflix.config.validation.PropertyChangeValidator;
import com.netflix.config.validation.ValidationException;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZooKeeperConfigurationSourceTest {

    public static void main(String[] args) throws  Exception{
        String zkConfigRootPath = "/archaius/config";

        CuratorFramework client =  CuratorFrameworkFactory.newClient("127.0.0.1:2181",
                new ExponentialBackoffRetry(1000, 3));
        client.start();

        ZooKeeperConfigurationSource zkConfigSource = new ZooKeeperConfigurationSource(client, zkConfigRootPath);
        zkConfigSource.start();

        DynamicWatchedConfiguration zkDynamicConfig = new DynamicWatchedConfiguration(zkConfigSource);
        ConfigurationManager.install(zkDynamicConfig);
        String myProperty = DynamicPropertyFactory.getInstance()
                .getStringProperty("com.fluxcapacitor.my.property", "<none>")
                .get();
        System.out.println(myProperty);
        DynamicStringProperty dynamicStringProperty = DynamicPropertyFactory.getInstance()
                .getStringProperty("com.fluxcapacitor.my.property", "<none>");

        dynamicStringProperty.addValidator(new PropertyChangeValidator(){
            @Override
            public void validate(String newValue) throws ValidationException {
                if (StringUtils.equals(newValue,"wuzhong")) {
                    System.out.println(newValue);
                    throw new ValidationException("Cannot be negative");
                }
            }
        });
        for (int i = 0; i < 100; i++) {
            Thread.sleep(10000);
            String myPropert = dynamicStringProperty.get();

            System.out.println(myPropert);
        }
    }
}
