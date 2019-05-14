package com.ximo.thinkingandprogramming.stage3.the7conucy.chap02.deadlock.v2;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 哲学家进餐
 *
 * @author xikl
 * @date 2019/5/14
 */
@Slf4j
public class Philosopher extends Thread {

    private static class Chopstick {
        private Integer index;

        public Chopstick(Integer index) {
            this.index = index;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }
    }


    private final Chopstick first;

    private final Chopstick second;

    private Random random;

    /**
     * 这里关键解决了 最大值的筷子 和最小值的筷子 的死锁问题  如 一个 人 应该拿起 5 和 1
     * 然后他应该先拿起1 然后发现 1 被拿了 那么他也就放弃了
     *
     * @param left
     * @param right
     */
    public Philosopher(Chopstick left, Chopstick right) {
        if (left.getIndex() < right.getIndex()) {
            this.first = left;
            this.second = right;
        } else {
            this.first = right;
            this.second = left;
        }
        random = new Random();
    }

    @Override
    public void run() {
        try {

            while (true) {
                TimeUnit.SECONDS.sleep(random.nextInt(1000));
                synchronized (first) {
                    synchronized (second) {
                        TimeUnit.SECONDS.sleep(random.nextInt(1000));
                    }
                }
            }
        } catch (InterruptedException e) {
            log.error("error", e);
        }

    }



}
