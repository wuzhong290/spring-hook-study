package com.demo.mybatis.xml;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;

import java.io.File;
import java.io.InputStream;

public class TestMain {

    public static void main(String[] args) {
        try {
            InputStream inputStream = FileUtils.openInputStream(new File("E:\\resources\\wuzhong290\\spring-hook-study\\mybatisPlugins\\src\\main\\java\\com\\demo\\mybatis\\xml\\mybatis-config.xml"));
            XPathParser parser = new XPathParser(inputStream, true, null, new XMLMapperEntityResolver());
            XNode xNode = parser.evalNode("/configuration/settings/setting[@name='autoMappingBehavior']");
            System.out.println(xNode.getStringAttribute("name"));
            System.out.println(xNode.getStringAttribute("value"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
