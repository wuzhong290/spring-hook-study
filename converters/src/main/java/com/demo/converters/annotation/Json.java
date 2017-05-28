package com.demo.converters.annotation;

import java.lang.annotation.*;

/**
 * Created by wuzhong on 2017/5/25.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Json {
}
