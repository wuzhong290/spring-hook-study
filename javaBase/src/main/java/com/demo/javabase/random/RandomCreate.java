package com.demo.javabase.random;

import org.apache.commons.lang3.StringUtils;

public class RandomCreate {

    protected int randomLen = 2;
    protected int index = 0;
    protected RandomCallback callback;
    /**
     *
     * @param random 随机数
     * @param position 随机数第几位
     * @param beginNum 某位随机数从几开始
     */
    public void createRandom(String random, int position,int beginNum){
        if(position  > randomLen){
            return;
        }
        position++;
        String temp;
        while (beginNum<10){
            temp = StringUtils.join(random, beginNum);
            if(StringUtils.length(temp) == randomLen){
                index++;
                callback.doCallBack(temp, index);
            }
            createRandom(temp, position,0);
            beginNum++;
        }
    }

    public static class Builder {
        private RandomCreate randomCreate = new RandomCreate();

        public Builder randomLen(int randomLen) {
            randomCreate.randomLen = randomLen;
            return this;
        }
        public Builder index(int index) {
            randomCreate.index = index;
            return this;
        }
        public Builder callback(RandomCallback callback) {
            randomCreate.callback = callback;
            return this;
        }
        public RandomCreate build() {
            validate();
            return randomCreate;
        }
        private void validate() {
            if (randomCreate.callback == null) {
                throw new IllegalStateException("Missing callback in property");
            }
        }
    }

    public static void main(String[] args) {
        RandomCreate randomCreate = new Builder()
                .randomLen(6)
                .index(0)
                .callback(new RandomCallbackImpl())
                .build();
        randomCreate.createRandom("",1,1);
    }
}
