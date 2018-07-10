package com.ximo.thread.design.pattern.chap1;

/**
 * @author 朱文赵
 * @date 2018/7/10 11:20
 * @description
 */
public class NonThreadSafeCounter {

    private int counter = 0;

    public void increment() {
        counter++;
    }

    public int get() {
        return counter;
    }



}
