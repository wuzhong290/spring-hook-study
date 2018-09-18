package com.demo.config;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicWatchedConfiguration;
import com.netflix.config.source.ZooKeeperConfigurationSource;
import org.apache.curator.framework.CuratorFramework;

public class ConfigMangerRegister {
    private static final String CONFIG_ROOT_PATH = "/archaius/config";

    private final CuratorFramework client;
    
    public ConfigMangerRegister(CuratorFramework client) {
        this.client = client;
    }

    /**
     * @throws Exception
     */
    public void start() throws Exception {
        String zkConfigRootPath = CONFIG_ROOT_PATH;

        ZooKeeperConfigurationSource zkConfigSource = new ZooKeeperConfigurationSource(client, zkConfigRootPath);
        zkConfigSource.start();

        DynamicWatchedConfiguration zkDynamicConfig = new DynamicWatchedConfiguration(zkConfigSource);
        ConfigurationManager.install(zkDynamicConfig);
    }
}
