package com.demo.discovery.util;

/**
 *
 * 通过UDP获取本机的IP地址
 */
public class LocalIp {

    private static String  localIp = LocalhostIpFetcher.fetchLocalIP();

    //do not new me
    private LocalIp(){

    }

    /**
     * 获取本机IP地址
     * @return 本机IP地址
     */
    public static String getLocalIp()
    {
        return  localIp;
    }


}
