package com.ximo.thinkingandprogramming.stage3.volatiles;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author xikl
 * @date 2019/6/4
 */
@Slf4j
public class VolatileTest {

    private volatile static int INIT_VALUE = 0;

    private final static int MAX_VALUE = 5;

    /**
     * 有volatile 关键字
     * 23:53:15.645 [writer] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTest - update the value to 1
     * 23:53:15.647 [reader] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTest - the value updated to 1
     * 23:53:16.648 [writer] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTest - update the value to 2
     * 23:53:16.648 [reader] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTest - the value updated to 2
     * 23:53:17.648 [writer] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTest - update the value to 3
     * 23:53:17.648 [reader] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTest - the value updated to 3
     * 23:53:18.648 [writer] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTest - update the value to 4
     * 23:53:18.648 [reader] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTest - the value updated to 4
     * 23:53:19.649 [writer] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTest - update the value to 5
     * 23:53:19.649 [reader] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTest - the value updated to 5
     *
     * 没有？
     * 那就是一直走下去 写线程
     * 读线程 无法读取到变化
     *
     * @param args
     */
    public static void main(String[] args) {
        new Thread(() -> {
            int localValue = INIT_VALUE;
            while (localValue < MAX_VALUE) {
                if (localValue != INIT_VALUE) {
                    log.info("the value updated to " + INIT_VALUE);
                    localValue = INIT_VALUE;
                }
            }
        }, "reader").start();

        new Thread(() -> {
            int localValue = INIT_VALUE;
            while (INIT_VALUE < MAX_VALUE) {
                log.info("update the value to " + ++localValue);
                INIT_VALUE = localValue;
                sleep(1);
            }
        }, "writer").start();

    }

    public static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
