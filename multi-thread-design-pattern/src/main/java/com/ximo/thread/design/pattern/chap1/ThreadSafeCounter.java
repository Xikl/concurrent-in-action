package com.ximo.thread.design.pattern.chap1;

/**
 * @author 朱文赵
 * @date 2018/7/10 12:05
 * @description
 */
public class ThreadSafeCounter {

    private int counter = 0;

    public synchronized void increment() {
        counter++;
    }

    public synchronized int get() {
        return counter;
    }



}
