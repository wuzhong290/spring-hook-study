点击E:\tools\zookeeper-3.5.3-beta\bin\zkServer.cmd,启动zookeeper

下载:https://issues.apache.org/jira/secure/attachment/12436620/ZooInspector.zip
运行: 解压缩后点击E:\tools\ZooInspector\build\zookeeper-dev-ZooInspector.jar后会出现以下界面

一、服务隔离
1、通过@ServiceDesc("zkRest")在类上增加，设置不同的名称，例如：zkRest
@Controller
@RequestMapping("/zk")
@ServiceDesc("zkRest")
public class ZkControler {

}
2、通过com.demo.config.ConfigMangerRegister
使用com.netflix.config.DynamicWatchedConfiguration把zk关于hystrix的线程池配置加载到内存
例如：
hystrix.threadpool.zkRest.coreSize=30
hystrix.threaegrapool.zkRest.maxQueueSize=5000

hystrix timeout config
hystrix.command.zkRest.zktest.execution.isolation.thread.timeoutInMilliseconds=2000

3、熔断机制
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(serviceName.split("\\.", 2)[0]))
                .andCommandKey(HystrixCommandKey.Factory.asKey(serviceName))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerEnabled(true)
                        .withCircuitBreakerRequestVolumeThreshold(1)
                        .withCircuitBreakerErrorThresholdPercentage(60)
                        .withExecutionTimeoutInMilliseconds(500)
                        .withCircuitBreakerSleepWindowInMilliseconds(30000))
        );
具体配置含义如下所示。
withCircuitBreakerEnabled：是否开启熔断机制，默认为true。
withCircuitBreakerForceClosed：是否强制关闭熔断开关，如果强制关闭了熔断开关，则请求不会被降级，一些特殊场景可以动态配置该开关，默认为false。
withCircuitBreakerForceOpen：是否强制打开熔断开关，如果强制打开可熔断开关，则请求强制降级调用getFallback处理，可以通过动态配置来打开该开关实现一些特殊需求，默认为false。
withCircuitBreakerErrorThresholdPercentage：如果在一个采样时间窗口内，失败率超过该配置，则自动打开熔断开关实现降级处理，即快速失败。默认配置下采样周期为10s，失败率为50%。
withCircuitBreakerRequestVolumeThreshold：在熔断开关闭合情况下，在进行失败率判断之前，一个采样周期内必须进行至少N个请求才能进行采样统计，目的是有足够的采样使得失败率计算正确，默认为20。
withCircuitBreakerSleepWindowInMilliseconds：熔断后的重试时间窗口，且在该时间窗口内只允许一次重试。即在熔断开关打开后，在该时间窗口允许有一次重试，如果重试成功，则将重置Health采样统计并闭合熔断开关实现快速恢复，否则熔断开关还是打开状态，执行快速失败。
