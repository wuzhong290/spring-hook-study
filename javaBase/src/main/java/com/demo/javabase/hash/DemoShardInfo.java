package com.demo.javabase.hash;

public class DemoShardInfo extends ShardInfo<Book> {
    private String name;

    public DemoShardInfo(String name,int weight){
        super(weight);
        this.name = name;
    }

    @Override
    protected Book createResource() {
        return new Book(getName());
    }

    @Override
    public String getName() {
        return this.name;
    }
}
