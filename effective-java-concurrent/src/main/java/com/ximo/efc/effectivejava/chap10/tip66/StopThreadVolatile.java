package com.ximo.efc.effectivejava.chap10.tip66;

import java.util.concurrent.TimeUnit;

/**
 * @author 朱文赵
 * @date 2019/3/13 15:36
 */
public class StopThreadVolatile {

    private static volatile boolean stopRequested;

    /**
     * 由于{{@link #stopRequested}} 被设置为线程共享的字段，所以他能保证每个线程都能看该字段的最新值
     * 所以这个方法能够正常结束
     */
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
