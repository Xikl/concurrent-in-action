package com.ximo.efc.effectivejava.chap10.tip69;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 学习循环栅栏
 *
 * @author 朱文赵
 * @date 2019/3/14 11:13
 */
public class CyclicBarrierApp {

    public static class Soldier implements Runnable {

        private String soldier;

        private CyclicBarrier cyclicBarrier;

        public Soldier(String soldier, CyclicBarrier cyclicBarrier) {
            this.soldier = soldier;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                cyclicBarrier.await();

                doWork();
                cyclicBarrier.await();

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        void doWork() throws InterruptedException {
            TimeUnit.MILLISECONDS.sleep(Math.abs(new Random().nextInt(1000)));
            System.out.println("任务完成");
        }
    }

    public static class BarrierRun implements Runnable {
        boolean flag;
        int n;

        public BarrierRun(boolean flag, int n) {
            this.flag = flag;
            this.n = n;
        }

        @Override
        public void run() {
            if (flag) {

                // 为真 则打印这个句话
                System.out.println("士兵" + n + "个任务完成");
            } else {
                // 首次进来flag为false 第一个await之后会打印下一句话
                System.out.println("士兵" + n + "个集合完毕");
                flag = true;
            }
        }
    }


    /**
     * 士兵：0报道
     * 士兵：1报道
     * 士兵：2报道
     * 士兵：3报道
     * 士兵：4报道
     * 士兵：5报道
     * 士兵：6报道
     * 士兵：7报道
     * 士兵：8报道
     * 士兵：9报道
     * 士兵10个集合完毕
     * 任务完成
     * 任务完成
     * 任务完成
     * 任务完成
     * 任务完成
     * 任务完成
     * 任务完成
     * 任务完成
     * 任务完成
     * 任务完成
     * 士兵10个任务完成
     *
     * @see CyclicBarrier#CyclicBarrier(int parties, Runnable barrierAction)
     * @param args
     */
    public static void main(String[] args) {
        final int n = 10;
        Thread[] allSoldier = new Thread[n];

        boolean flag = false;
        // 第二个参数是当他结束后才做的事情
        CyclicBarrier cyclicBarrier = new CyclicBarrier(n, new BarrierRun(flag, n));
        for (int i = 0; i < n; i++) {
            System.out.println("士兵：" + i + "报道");
            allSoldier[i] = new Thread(new Soldier("士兵" + i, cyclicBarrier));
            allSoldier[i].start();
        }

    }

}
