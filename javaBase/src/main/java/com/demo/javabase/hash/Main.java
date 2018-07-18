package com.demo.javabase.hash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DemoShardInfo info1 = new DemoShardInfo("demo1",1);
        DemoShardInfo info2 = new DemoShardInfo("demo2",1);
        DemoShardInfo info3 = new DemoShardInfo("demo3",1);
        DemoShardInfo info4 = new DemoShardInfo("demo4",1);
        DemoShardInfo info5 = new DemoShardInfo("demo5",1);
        DemoShardInfo info6 = new DemoShardInfo("demo6",1);

        List<DemoShardInfo> shards = new ArrayList<>();
        shards.add(info1);
        shards.add(info2);
        shards.add(info3);
        shards.add(info4);
        shards.add(info5);
        shards.add(info6);

        Sharded<Book, DemoShardInfo> sharded = new Sharded<>(shards);

        Collection<DemoShardInfo> infos =  sharded.getAllShardInfo();

        Book book = sharded.getShard("dddd");

        System.out.println("");
    }
}
