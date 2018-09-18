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