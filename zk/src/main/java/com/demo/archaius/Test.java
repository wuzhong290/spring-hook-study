package com.demo.archaius;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Test {

    public static void main(String[] args) {
        String s = "http://182.131.0.104:8090/GP100017_month/index.jsp?UserID=CJNS08@ITVP&UserToken=5RizN5RizN4WhxwYfGBa384KdXwq16Nc&RoleID=88e40060-6fa2-463f-8747-ad4d8d83c36f&ProductID=100017&NickName=m5iNU9QlBj&ReturnURL=http://182.131.0.104:8080/JLMonthWebNew/home/gameLogout.jsp?PageURL=aHR0cDovLzE4Mi4xMzEuMC4xMDQ6ODA4MC9KTE1vbnRoV2ViTmV3L2hhbGxQYWdlL2dhbWVIYWxsUGFnZS5qc3A";

        System.out.println(s.substring(0,s.lastIndexOf("?PageURL")));

        try {
            System.out.println(URLEncoder.encode("游戏不存在","utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
