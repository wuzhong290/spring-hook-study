package com.demo.resolvers.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.deser.FromXmlParser;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.dataformat.xml.util.DefaultXmlPrettyPrinter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class XmlMapperTest {

    public static void main(String[] args) {
        XmlMapper xmlMapper = new XmlMapper();

        final Issue86 before = new Issue86("0",
                Arrays.asList(new Issue86("0.1",
                                Arrays.asList(new Issue86("0.1.1", null))),
                        new Issue86("0.2", null),
                        new Issue86("0.3",
                                Arrays.asList(
                                        new Issue86("0.3.1", null)))));

        final XmlMapper mapper = new XmlMapper();

        try {
            final String xml = mapper.writeValueAsString(before);
            System.out.println(xml);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        Persons ps = new Persons();
        Person p = new Person();
        p.setName("wz");
        ps.setIndex("1");
        ps.setPerson(p);
        String xml = "<response><wrapper><item id=\"1\"><a>x</a><b>y</b></item><item id=\"2\"><a>y</a><b>x</b></item></wrapper></response>";
        try {
            Response res = xmlMapper.readValue(xml, Response.class);
            System.out.println(xmlMapper.writeValueAsString(res));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(xmlMapper.writeValueAsString(ps));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static class Response
    {
        @JacksonXmlProperty(localName = "wrapper")
        public List<Item> items;
    }

    public static class Item
    {
        public String id;
        public String a;
        public String b;
    }

    @JacksonXmlRootElement(localName = "test")
    public static class Issue86 {

        @JacksonXmlProperty(localName = "id", isAttribute = true)
        private String id;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "test")
        private List<Issue86> children;

        public Issue86() {}

        public Issue86(final String id, final List<Issue86> children) {
            this.id = id;
            this.children = children;
        }

        @Override
        public boolean equals(final Object other) {
            if (other == null) {
                return false;
            }

            if (other == this) {
                return true;
            }

            if (!(other instanceof Issue86)) {
                return false;
            }

            final Issue86 otherIssue86 = (Issue86) other;
            return otherIssue86.id.equals(id) && otherIssue86.children.equals(children);
        }
    }
}

class Persons {
    @JacksonXmlProperty(isAttribute=true)
    private String index;
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}


class Person{
    @JacksonXmlProperty(isAttribute=true)
    private String name;

    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}