package com.ximo.efc.effectivejava.chap10.tip69;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * @author 朱文赵
 * @date 2019/3/14 10:47
 */
public class CountDownLatchApp {

    public static void main(String[] args) {

    }

    /**
     * 该方法中的 线程池 必须创建和 concurrency 大小一样的线程池 否则该程序永远不会结束
     * 会导致有些线程 饥饿性死锁
     *
     * @param executor
     * @param concurrency
     * @param action
     * @return
     * @throws InterruptedException
     */
    public static long time(Executor executor, int concurrency, Runnable action) throws InterruptedException {

        final CountDownLatch ready = new CountDownLatch(concurrency);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            Runnable task = () -> {
                ready.countDown();
                try {
                    start.await();
                    action.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    done.countDown();
                }
            };
            executor.execute(task);
        }

        // 等所有线程都 取得 cpu调度
        ready.await();
        // 开始
        long startTime = System.nanoTime();
        start.countDown();
        // 等待所有的完成
        done.await();
        return System.nanoTime() - startTime;

    }
}
