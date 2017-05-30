package com.demo.resolvers.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by thinkpad on 2017/5/28.
 */
public class GetArgumentStrategy implements ResolveArgumentStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(GetArgumentStrategy.class);
    @Override
    public String resolveArgumentValue(HttpServletRequest request) {
        return request.getQueryString();
    }
}
