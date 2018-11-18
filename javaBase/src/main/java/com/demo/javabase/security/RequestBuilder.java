package com.demo.javabase.security;

import java.util.*;

public class RequestBuilder {
    private Map<String, String> paramsMap = null;

    public RequestBuilder() {
        paramsMap = new TreeMap<>();
    }

    public RequestBuilder addParameter(String key, String value) {
        paramsMap.put(key, value);
        return this;
    }

    public String toLinkString() {
        List<String> keys = new ArrayList<String>(paramsMap.keySet());
        Collections.sort(keys);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = paramsMap.get(key);
            builder.append(key).append("=").append(value);
            //拼接时，不包括最后一个&字符
            if (i != keys.size() - 1) {
                builder.append("&");
            }
        }
        return builder.toString();
    }

    public Map<String, String> getParamsMap() {
        return paramsMap;
    }
}