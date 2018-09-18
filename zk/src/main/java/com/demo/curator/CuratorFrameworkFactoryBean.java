package com.demo.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;


/**
 * 获取CuratorFramework 客户端
 */
public class CuratorFrameworkFactoryBean implements FactoryBean<CuratorFramework>, InitializingBean, DisposableBean {
  private final static Logger logger = LoggerFactory.getLogger(CuratorFrameworkFactoryBean.class);

  private CuratorFramework curator;
  private String connectString;
  private RetryPolicy retryPolicy;
  private Integer sessionTimeout;
  private String namespace;

  @Override
  public void afterPropertiesSet() throws Exception {

    if(connectString  == null){
      return;
    }

    CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();

    builder.connectString(connectString);

    if (retryPolicy == null) {
      retryPolicy = new ExponentialBackoffRetry(1000, 3);
    }
    builder.retryPolicy(retryPolicy);

    if (sessionTimeout != null) {
      builder.sessionTimeoutMs(sessionTimeout);
    }

    if (namespace != null) {
      builder.namespace(namespace);
    }

    curator = builder.build();
    curator.start();
    logger.info("start zookeeper client success. connected to {}", connectString);
  }

  @Override
  public void destroy() throws Exception {
    curator.close();
  }

  @Override
  public CuratorFramework getObject() throws Exception {
    return curator;
  }

  @Override
  public Class<?> getObjectType() {
    return CuratorFramework.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

  public String getConnectString() {
    return connectString;
  }

  public void setConnectString(String connectString) {
    this.connectString = connectString;
  }

  public Integer getSessionTimeout() {
    return sessionTimeout;
  }

  public void setSessionTimeout(Integer sessionTimeout) {
    this.sessionTimeout = sessionTimeout;
  }

  public RetryPolicy getRetryPolicy() {
    return retryPolicy;
  }
  
  public void setRetryPolicy(RetryPolicy retryPolicy) {
    this.retryPolicy = retryPolicy;
  }

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

}