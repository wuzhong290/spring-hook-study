package com.demo.discovery.client.strategy;

import java.util.List;

/**
 * 选择器
 */
public interface Strategy {

    public <T> T getInstance(List<T> all);

}
