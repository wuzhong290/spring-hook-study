package com.demo.discovery.server.model;

import com.demo.discovery.util.URIBuilder;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonRootName;


/**
 * 服务实例
 */
@JsonRootName("details")
public class InstanceDetails {

    /**
     * 构造器模式
     */
    public static class InstanceDetailBuilder {

        private static final String schema = "http://";

        private InstanceDetails instanceDetail;

        public InstanceDetailBuilder() {
            instanceDetail = new InstanceDetails();
        }

        public InstanceDetailBuilder linstenAddress(String linstenAddress) {
            instanceDetail.linstenAddress = linstenAddress;
            return this;
        }

        public InstanceDetailBuilder context(String context) {
            instanceDetail.context = context;
            return this;
        }

        public InstanceDetailBuilder serviceType(String serviceType) {
            instanceDetail.serviceType = serviceType;
            return this;
        }

        public InstanceDetailBuilder methodName(String methodName) {
            instanceDetail.methodName = methodName;
            return this;
        }

        public InstanceDetailBuilder controllerRequestMapping(String controllerRequestMapping) {
            instanceDetail.controllerRequestMapping = controllerRequestMapping;
            return this;
        }

        public InstanceDetailBuilder methodReuqestMapping(String methodReuqestMapping) {
            instanceDetail.methodRequestMapping = methodReuqestMapping;
            return this;
        }

        /**
         * 是否降级
         * @param degrade 降级
         * @return  builder
         */
        public InstanceDetailBuilder degrade(boolean degrade) {
            instanceDetail.degrade = degrade;
            return this;
        }

        public InstanceDetails build() {
            //全部的url：http://localhost:880/context/sigin/add
            instanceDetail.requestUrl = this.buildRequestUrl();
            instanceDetail.serviceName = getServiceName();
            return this.instanceDetail;
        }

        /**
         * 获取服务名称： serviceType+RelativeURL： UserCenter./siginin
         *
         * @return 服务名称
         */
        private String getServiceName() {
            return instanceDetail.serviceType + "." + instanceDetail.methodName;
        }

        private String buildRequestUrl() {
                URIBuilder builder = new URIBuilder(schema + instanceDetail.linstenAddress);
                builder.addPath(instanceDetail.context);
                builder.addPath(instanceDetail.controllerRequestMapping);
                builder.addPath(instanceDetail.methodRequestMapping);
                return builder.build();
        }

    }

    public InstanceDetails() {

    }

    public InstanceDetails(String serviceName, String requestUrl) {
        this.serviceName = serviceName;
        this.requestUrl = requestUrl;
    }
    //Tomcat 监听的地址, 例如：127.0.0.0:8081
    private String linstenAddress;

    //web在tomcat中的Context,例如resources
    private String context;

    //服务的类型,某一类的服务
    private String serviceType;

    //controller上方法的名称.
    private String methodName;

    //controller上mapping的url
    private String controllerRequestMapping;

    //controller方法上mapping的URL
    private String methodRequestMapping;

    //服务的名称
    private String serviceName;

    // 服务请求的URL
    private String requestUrl;
    //是否降级
    @JsonIgnore
    private boolean degrade = false;

    public String fetchContextUrl() {

         URIBuilder builder = new URIBuilder(InstanceDetailBuilder.schema + linstenAddress);
         builder.addPath(context);
         String  url = builder.build().toString();

        return url;
    }


    /**
     * 获取剔除了 [http://ip:port/context]/get/request 后面的部分
     *
     * @return /get/request
     */
    public String fetchSubUrl() {

        String controller;
        if (StringUtils.isEmpty(controllerRequestMapping)) {
            controller = "/";
        } else {
            //如果不是 / 开始，则加上
            controller = controllerRequestMapping.startsWith("/") ? controllerRequestMapping : "/" + controllerRequestMapping;
            //如果是 /结尾，则去掉/
            controller = controller.endsWith("/") ? controller.substring(0, controller.length() - 1) : controller;
        }

        String method;
        if (StringUtils.isEmpty(methodRequestMapping)) {
            method = "/";
        } else {
            //如果不是 / 开始，则加上
            method = methodRequestMapping.startsWith("/") ? methodRequestMapping : "/" + methodRequestMapping;
        }

        return controller + method;
    }

    public String getLinstenAddress() {
        return linstenAddress;
    }


    public String getContext() {
        return context;
    }

    public String getServiceType() {
        return serviceType;
    }


    public String getMethodName() {
        return methodName;
    }


    public String getControllerRequestMapping() {
        return controllerRequestMapping;
    }


    public String getMethodRequestMapping() {
        return methodRequestMapping;
    }

    @JsonIgnore
    public boolean isDegrade() {
        return degrade;
    }

    @JsonIgnore
    public void setDegrade(boolean degrade) {
        this.degrade = degrade;
    }


    public String getServiceName() {
        return serviceName;
    }


    public String getRequestUrl() {
        return requestUrl;
    }

    @Override
    public String toString() {
        return "InstanceDetails{" +
                "linstenAddress='" + linstenAddress + '\'' +
                ", context='" + context + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", methodName='" + methodName + '\'' +
                ", controllerRequestMapping='" + controllerRequestMapping + '\'' +
                ", methodRequestMapping='" + methodRequestMapping + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", degrade=" + degrade +
                '}';
    }
}
