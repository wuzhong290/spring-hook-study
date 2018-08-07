package com.demo.mybatis.xml;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class TestMain {

    public static void main(String[] args) {
        try {
            InputStream inputStream = FileUtils.openInputStream(new File("E:\\resources\\wuzhong290\\spring-hook-study\\mybatisPlugins\\src\\main\\java\\com\\demo\\mybatis\\xml\\mybatis-config.xml"));
            XPathParser parser = new XPathParser(inputStream, true, null, new XMLMapperEntityResolver());
            XNode xNode = parser.evalNode("/configuration/settings/setting[@name='autoMappingBehavior']");
            System.out.println(xNode.getStringAttribute("name"));
            System.out.println(xNode.getStringAttribute("value"));

            InputStream inputStreamMapper = FileUtils.openInputStream(new File("E:\\resources\\wuzhong290\\spring-hook-study\\mybatisPlugins\\src\\main\\java\\com\\demo\\mybatis\\xml\\LogPageMapper.xml"));
            XPathParser parserMapper = new XPathParser(inputStreamMapper, true, null, new XMLMapperEntityResolver());
            XNode xNodeSel = parserMapper.evalNode("/mapper/select[@id='selectByExample']");
            System.out.println(xNodeSel.getStringAttribute("parameterType"));
            List<XNode> childNodes =  xNodeSel.getChildren();
            for (XNode node: childNodes) {
                System.out.println(node.getName() + ":" +node.getStringBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
