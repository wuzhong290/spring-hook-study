package com.demo.mybatis.xml;

import com.demo.druid.model.LogPageExample;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;

import java.io.File;
import java.io.InputStream;

public class SqlSourceMain {
    //XMLScriptBuilder 的测试使用
    public static void main(String[] args)  throws  Exception {
        Configuration configuration = TestMapperXmlParserMain.getConfiguration();

        InputStream inputStream = FileUtils.openInputStream(new File("E:\\resources\\wuzhong290\\spring-hook-study\\mybatisPlugins\\src\\main\\java\\com\\demo\\mybatis\\xml\\LogPageMapper.xml"));
        XPathParser parser = new XPathParser(inputStream, true, configuration.getVariables(), new XMLMapperEntityResolver());
        XNode script = parser.evalNode("/mapper/select[@id='selectByExample']");
        XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, LogPageExample.class);
        SqlSource source = builder.parseScriptNode();
        LogPageExample example = new LogPageExample();
        example.setOrderByClause("USERID");
        example.setDistinct(true);
        BoundSql boundSql = source.getBoundSql(example);
        System.out.println(boundSql.getSql());
    }
}
