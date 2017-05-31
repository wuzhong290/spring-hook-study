package com.demo.resolvers.annotation;

import java.lang.annotation.*;

/**
 * Created by wuzhong on 2017/5/31.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Json {
}
