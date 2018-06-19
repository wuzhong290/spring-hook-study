package com.demo.resolvers.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by thinkpad on 2017/5/28.
 */
public class DefaultArgumentStrategy implements ResolveArgumentStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultArgumentStrategy.class);
    @Override
    public String resolveArgumentValue(HttpServletRequest request) {
        StringBuffer buffer = new StringBuffer();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null){
                buffer.append(line);
            }
        } catch (IOException e) {
        }
        return buffer.toString();
    }
}
