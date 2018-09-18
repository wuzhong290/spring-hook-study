package com.demo.discovery.server.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface ServiceDesc {
    /**
     * 可选，如果不填写，则设置为context
     * @return 服务的名称。
     */
    String value() default "";
    /**
     * 是否对请求进行降级， 默认false
     * @return  是否降级
     */
    boolean degrade() default  false;
}
