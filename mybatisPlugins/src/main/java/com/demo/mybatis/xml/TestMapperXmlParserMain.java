package com.demo.mybatis.xml;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.InputStream;

public class TestMapperXmlParserMain {

    public static void main(String[] args) {
        xmlMapperBuilder();

    }

    public static Configuration getConfiguration(){
        try {
            InputStream inputStream = FileUtils.openInputStream(new File("E:\\resources\\wuzhong290\\spring-hook-study\\mybatisPlugins\\src\\main\\java\\com\\demo\\mybatis\\xml\\mybatis-config.xml"));
            XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(inputStream, null, null);
            Configuration configuration = xmlConfigBuilder.getConfiguration();
            return configuration;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void xmlMapperBuilder(){
        Configuration configuration =getConfiguration();
        try {
            InputStream inputStreamMapper = FileUtils.openInputStream(new File("E:\\resources\\wuzhong290\\spring-hook-study\\mybatisPlugins\\src\\main\\java\\com\\demo\\mybatis\\xml\\LogPageMapper.xml"));
            InputStreamResource mapperLocation =new InputStreamResource(inputStreamMapper);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperLocation.getInputStream(),
            configuration, mapperLocation.toString(), configuration.getSqlFragments());
            xmlMapperBuilder.parse();
            System.out.println("");
        } catch (Exception e) {
        }
    }

}
