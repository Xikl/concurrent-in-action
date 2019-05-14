package com.ximo.thinkingandprogramming.stage3.the7conucy.chap02;

import java.util.concurrent.TimeUnit;

/**
 * @author xikl
 * @date 2019/4/11
 */
public class HelloWord {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> System.out.println("hello from new thread"));

        thread.start();

//        Thread.yield();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("hello from main thread");
        thread.join();

    }

}
