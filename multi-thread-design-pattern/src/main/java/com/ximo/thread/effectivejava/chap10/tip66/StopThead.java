package com.ximo.thread.effectivejava.chap10.tip66;

import java.util.concurrent.TimeUnit;

/**
 * @author 朱文赵
 * @date 2019/3/13 15:19
 */
public class StopThead {

    private static boolean stopRequested;

    /**此程序将无法终止
     * jvm将方法转化为如下结构
     * if (!stopRequested){
     *      while (true){
     *          i++;
     *      }
     * }
     *
     * This optimization is known as hoisting, and it is precisely what the OpenJDK
     * Server VM does. The result is a liveness failure: the program fails to make
     * progress. One way to fix the problem is to synchronize access to the
     * stopRequested field. This program terminates in about one second,
     * 活性失败
     *
     *
     * */
    public static void main(String[] args)
            throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested) {
                i++;
            }
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);

        stopRequested = true;
    }
}
