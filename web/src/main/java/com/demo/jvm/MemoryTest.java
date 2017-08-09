package com.demo.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzhong on 2017/7/5.
 */
public class MemoryTest {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(1000);
        endlessLoop();
    }


    public static void endlessLoop() throws InterruptedException {
        List<MemoryTest> str = new ArrayList<>();
        while (true) {
            str.add(new MemoryTest());
        }
    }
}
