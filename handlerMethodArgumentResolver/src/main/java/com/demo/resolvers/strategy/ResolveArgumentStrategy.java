package com.demo.resolvers.strategy;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by thinkpad on 2017/5/28.
 */
public interface ResolveArgumentStrategy {

    String resolveArgumentValue(HttpServletRequest request);
}
