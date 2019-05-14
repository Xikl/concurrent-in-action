package com.ximo.thinkingandprogramming.stage3.the7conucy.chap02.deadlock.v1;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 哲学家进餐问题
 *
 * @author xikl
 * @date 2019/5/14
 */
@Slf4j
public class Philosopher extends Thread {

    private static class Chopstick {
    }

    private final Chopstick left;

    private final Chopstick right;

    private Random random;

    public Philosopher(Chopstick left, Chopstick right) {
        this.left = left;
        this.right = right;
        random = new Random();
    }

    @Override
    public void run() {
        try {

            while (true) {
                TimeUnit.SECONDS.sleep(random.nextInt(1000));
                synchronized (left) {
                    synchronized (right) {
                        TimeUnit.SECONDS.sleep(random.nextInt(1000));
                    }
                }
            }
        } catch (InterruptedException e) {
            log.error("error", e);
        }
    }
}
