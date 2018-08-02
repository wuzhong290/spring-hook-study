package com.demo.javabase.random;

public class RandomCallbackImpl implements RandomCallback {
    @Override
    public void doCallBack(String random, int index) {
        System.out.println(random);
    }
}
