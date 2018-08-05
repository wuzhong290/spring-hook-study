package com.demo.javabase;

import java.util.HashMap;
import java.util.Map;

public class Test1 {

    public Test1(Map<String,String> stringMap) {
        stringMap.put("name","dddd");
    }

    public void modifyName(Map<String,String> stringMap){
        stringMap.put("name","dddd1");
        class Inner{
            public Inner(Map<String,String> stringMap) {
                stringMap.put("name","inner");
                stringMap = new HashMap<>();
                stringMap.put("name","inner1");
                System.out.println(stringMap.get("name"));
            }
        }
        new Inner(stringMap);
    }

    public static void main(String[] args) {
        String returnURLDecode = "http://110.190.90.140:8296/BackFromSP";
        System.out.println(returnURLDecode.contains("?"));
        String  name = "wuzhong";
        final Map<String,String> stringMap = new HashMap<>();
        stringMap.put("name",name);
        Test1 test1 = new Test1(stringMap);
        test1.modifyName(stringMap);
        System.out.println(stringMap.get("name"));
    }
}
