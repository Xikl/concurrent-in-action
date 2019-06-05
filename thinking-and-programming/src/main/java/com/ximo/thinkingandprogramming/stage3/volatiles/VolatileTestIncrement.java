package com.ximo.thinkingandprogramming.stage3.volatiles;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * test 内存变量进行++
 *
 * @author xikl
 * @date 2019/6/5
 */
@Slf4j
public class VolatileTestIncrement {

    private volatile static int INIT_VALUE = 0;

    private final static int MAX_VALUE = 10;

    /**
     * 23:40:42.565 [adder-1] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTestIncrement - adder1进行加法：1
     * 23:40:42.565 [adder-2] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTestIncrement - adder2进行加法2
     * 23:40:43.568 [adder-2] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTestIncrement - adder2进行加法3
     * 23:40:43.568 [adder-1] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTestIncrement - adder1进行加法：3
     * 23:40:44.569 [adder-2] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTestIncrement - adder2进行加法5
     * 23:40:44.569 [adder-1] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTestIncrement - adder1进行加法：4
     * 23:40:45.569 [adder-1] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTestIncrement - adder1进行加法：6
     * 23:40:45.569 [adder-2] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTestIncrement - adder2进行加法6
     * 23:40:46.570 [adder-1] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTestIncrement - adder1进行加法：8
     * 23:40:46.570 [adder-2] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTestIncrement - adder2进行加法7
     * 23:40:47.571 [adder-1] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTestIncrement - adder1进行加法：9
     * 23:40:47.571 [adder-2] INFO com.ximo.thinkingandprogramming.stage3.volatiles.VolatileTestIncrement - adder2进行加法10
     *
     *  因为有写操作， 他会去主存中拿取
     *
     * @param args
     */
    public static void main(String[] args) {
        new Thread(() -> {
            while (INIT_VALUE < MAX_VALUE) {
                log.info("adder1进行加法：{}", ++INIT_VALUE);
                sleep(1);
            }
        }, "adder-1").start();

        new Thread(() -> {
            while (INIT_VALUE < MAX_VALUE) {
                log.info("adder2进行加法{}", ++INIT_VALUE);
                sleep(1);
            }
        }, "adder-2").start();

    }

    public static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }






}
