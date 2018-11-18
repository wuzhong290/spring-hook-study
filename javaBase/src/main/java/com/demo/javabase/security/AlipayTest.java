package com.demo.javabase.security;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public class AlipayTest {

    private static RequestBuilder newRequestBuider(String tradeNo) {
        RequestBuilder builder = new RequestBuilder();
        builder.addParameter("service", "single_trade_query")
                .addParameter("partner", "partner")
                .addParameter("out_trade_no", tradeNo)
                .addParameter("_input_charset", "utf-8");

        String signStr = null;
        try {
            signStr = RSAUtils.sign(builder.toLinkString().getBytes("utf-8"), RSATester.privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.addParameter("sign", signStr)
                .addParameter("sign_type", "RSA");
        return builder;
    }

    public static void main(String[] args) {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
        for (Map.Entry<String, String> entry : newRequestBuider("tradeNo").getParamsMap().entrySet()) {
            request.add(entry.getKey(), entry.getValue());
        }
        //https://mapi.alipay.com/gateway.do?
        //application/x-www-form-urlencoded
        System.out.println("");
    }
}
