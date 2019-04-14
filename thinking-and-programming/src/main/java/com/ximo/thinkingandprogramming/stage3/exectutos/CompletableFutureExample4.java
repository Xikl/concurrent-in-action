package com.ximo.thinkingandprogramming.stage3.exectutos;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

/**
 * @author xikl
 * @date 2019/4/14
 */
@Slf4j
public class CompletableFutureExample4 {

    public static void main(String[] args) throws InterruptedException {
//        thenAcceptBoth();
//        acceptEither();
//        runAfterBoth();
//        thenCombine();
        thenCompose();
        Thread.currentThread().join();
    }

    /**
     * @see CompletableFuture#thenAcceptBoth(CompletionStage, BiConsumer)  会使用第二个线程进行 action操作
     * 19:10:56.837 [ForkJoinPool.commonPool-worker-2] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - start another CompletableFuture
     * 19:10:56.837 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - start
     * 19:10:59.840 [ForkJoinPool.commonPool-worker-2] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - first: hello world, other: 100
     */
    private static void thenAcceptBoth() {
        CompletableFuture.supplyAsync(() -> {
            log.info("start");
            sleep(2);
            return "hello world";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            log.info("start another CompletableFuture");
            sleep(3);
            return 100;
        }), (s, i) -> log.info("first: {}, other: {}", s, i))
        .join();
    }

    /**
     * 任意接受一个 但是都是执行的
     * 19:07:19.818 [ForkJoinPool.commonPool-worker-2] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - start1
     * 19:07:19.818 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - start
     * 19:07:21.820 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - hello world
     *
     */
    private static void acceptEither() {
        CompletableFuture.supplyAsync(() -> {
            log.info("start");
            sleep(2);
            return "hello world";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            log.info("start1");
            sleep(3);
            return "hello world1";
        }), log::info).join();
    }

    /**
     * 不关心回调输出
     * 19:11:19.479 [ForkJoinPool.commonPool-worker-2] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - start1
     * 19:11:19.479 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - start
     * 19:11:21.481 [ForkJoinPool.commonPool-worker-2] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - all done
     *
     */
    private static void runAfterBoth() {
        CompletableFuture.supplyAsync(() -> {
            log.info("start");
            sleep(2);
            return "hello world";
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            log.info("start1");
            sleep(2);
            return "hello world1";
        }), () -> log.info("all done"))
        .join();
    }

    /**
     * 19:16:02.788 [ForkJoinPool.commonPool-worker-2] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - start1
     * 19:16:02.788 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - start
     * 19:16:04.790 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - end1
     * 19:16:04.790 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - any done
     * 19:16:05.790 [ForkJoinPool.commonPool-worker-2] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - end2
     *
     */
    private static void runAfterEither() {
        CompletableFuture.supplyAsync(() -> {
            log.info("start");
            sleep(2);
            log.info("end1");
            return "hello world";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            log.info("start1");
            sleep(3);
            log.info("end2");
            return "hello world1";
        }), () -> log.info("any done"))
                .join();
    }

    /**
     * 19:22:26.330 [ForkJoinPool.commonPool-worker-2] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - start2
     * 19:22:26.330 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - start1
     * 19:22:28.334 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - end1
     * 19:22:28.334 [ForkJoinPool.commonPool-worker-2] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - end2
     * 19:22:28.334 [ForkJoinPool.commonPool-worker-2] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - 字符串长度加上数字等于 1011
     *
     */
    private static void thenCombine() {
        CompletableFuture.supplyAsync(() -> {
            log.info("start1");
            sleep(2);
            log.info("end1");
            return "hello world";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            log.info("start2");
            sleep(2);
            log.info("end2");
            return 1000;
            // BiFunction
        }), (str, i) -> str.length() + i).whenComplete((data, error) -> log.info("字符串长度加上数字等于 {}", data, error))
        .join();
    }

    /**
     * 23:49:38.996 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - start1
     * 23:49:40.999 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - end1
     * 23:49:40.999 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - compose start
     * 23:49:43.000 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - compose end
     * 23:49:43.000 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample4 - result: 11
     *
     * 上一个future的值会变成下一个的输入
     */
    private static void thenCompose() {
        CompletableFuture.supplyAsync(() -> {
            log.info("start1");
            sleep(2);
            log.info("end1");
            return "hello world";
        }).thenCompose(prev -> CompletableFuture.supplyAsync(() -> {
            log.info("compose start");
            sleep(2);
            log.info("compose end");
            return prev.length();
        })).thenAccept(data -> log.info("result: {}", data))
                .join();

    }


    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
