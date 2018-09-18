package com.demo.discovery.server.register;

import com.demo.discovery.server.annotation.ServiceDesc;

/**
 *  Register
 * Created by chunhua.zhang@yoho.cn on 2015/12/30.
 */
public interface IRegister {

    /**
     * 注册一个服务
     *
     * @param context         tomcat 的 context, 例如 users
     * @param listenAddress   tomcat的监听地址，例如：127.0.0.1:8081
     * @param serviceType     服务的类别，取controller上{@link ServiceDesc}标签传入的参数, 如果没传，默认是 context
     * @param methodName      方法的名称，取controller上{@link ServiceDesc}标签传入的参数, 如果没传，默认是 方法名称
     * @param method_path     method上的requestMapping标记的URL, 可能为空
     * @param controller_path controller类上的requestMapping标记的URL, 可能为空
     * @param serviceDesc 方法上的 {@link ServiceDesc} 注解内容
     */
    void register(String context, String listenAddress, String serviceType, String methodName, String method_path, String controller_path, ServiceDesc serviceDesc);
}