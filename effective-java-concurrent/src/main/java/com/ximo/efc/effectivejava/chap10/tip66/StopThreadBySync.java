package com.ximo.efc.effectivejava.chap10.tip66;

import java.util.concurrent.TimeUnit;

/**
 * @author 朱文赵
 * @date 2019/3/13 15:32
 */
public class StopThreadBySync {

    private static boolean stopRequested;

    /** 新加入两个同步方法 */
    private static synchronized void requestStop() {
        stopRequested = true;
    }

    private static synchronized boolean stopRequested() {
        return stopRequested;
    }

    /**
     * 此程序能够正常终止
     * Synchronization is not guaranteed to work unless both
     * read and write operations are synchronized.
     * 只有在读和写同时被同步的时候才能够抱着线程安全
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args)
            throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested()) {
                i++;
            }
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);

        requestStop();
    }



}
