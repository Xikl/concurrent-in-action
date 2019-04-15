package com.ximo.thinkingandprogramming.stage3.exectutos;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 终止操作
 *
 * @author xikl
 * @date 2019/4/15
 */
@Slf4j
public class CompletableFutureExample5 {


    public static void main(String[] args) throws Exception {
//        getNow();
//        complete();
//        join();
//        completeExceptionally();
        CompletableFuture<String> stringCompletableFuture = handleError();
        stringCompletableFuture.whenComplete((v, t) -> log.info("完成：{}", v, t));
        Thread.currentThread().join();

    }

    /**
     * 22:31:13.658 [main] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - valueIfAbsent
     * 22:31:13.658 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - start
     * 22:31:15.672 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - end
     * 22:31:15.672 [main] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - final result, hello word
     * 如果当前的没有返回 那么就用valueIfAbsent代替
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void getNow() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("start");
            sleep(2);
            log.info("end");
            return "hello word";
        });
        String valueIfAbsent = completableFuture.getNow("valueIfAbsent");
        log.info(valueIfAbsent);
        String result = completableFuture.get();
        log.info("final result, {}", result);
    }

    /**
     * 1. 第一种的时候，主线程快于future然后 future有机会去执行 compete获得true 拿到一个default的值
     * 22:43:09.962 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - start
     * 22:43:10.961 [main] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - 结果： true
     * 22:43:10.962 [main] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - 你好世界
     * 22:43:12.964 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - end
     *
     * <>
     *     CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
     *             log.info("start");
     *             sleep(3);
     *             log.info("end");
     *             return "hello word";
     *         });
     *         sleep(1);
     *         boolean result = completableFuture.complete("你好世界");
     *         log.info("结果： {}", result);
     *         log.info(completableFuture.get());
     * </>
     *
     * 1.1 主线程快于future future压根没有机会去执行 主线程就获得了complete的值
     * 22:49:49.450 [main] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - 结果： true
     * 22:49:49.453 [main] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - 你好世界
     * <>
     *       CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
     *             log.info("start");
     *             sleep(3);
     *             log.info("end");
     *             return "hello word";
     *         });
     *         boolean result = completableFuture.complete("你好世界");
     *         log.info("结果： {}", result);
     *         log.info(completableFuture.get());
     * </>
     *
     *
     * 2. 第二种的时候 当CompletableFuture快于主线程进行操作的时候， complete返回false future.get()的时候 拿到真正的值
     *
     * 22:37:16.747 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - start
     * 22:37:16.749 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - end
     * 22:37:17.746 [main] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - 结果： false
     * 22:37:17.747 [main] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - hello word
     *
     * <>
     *        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
     *             log.info("start");
     * //            sleep(3);
     *             log.info("end");
     *             return "hello word";
     *         });
     *         sleep(1);
     *         boolean result = completableFuture.complete("你好世界");
     *         log.info("结果： {}", result);
     *         log.info(completableFuture.get());
     * </>
     * 适用范围： 当我们需要在两个地方选择的时候，如果 A 比较慢 我们没有等到 那么我们就用B的代替 或者 谁快 我们用谁的
     *
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void complete() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("start");
            sleep(3);
            log.info("end");
            return "hello word";
        });
        boolean result = completableFuture.complete("你好世界");
        log.info("结果： {}", result);
        log.info(completableFuture.get());
    }

    /**
     * join 和 get保持一致 get 回抛出异常 join 不会 {@link CompletableFuture#get()}
     * 22:55:09.407 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - start
     * 22:55:12.410 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - end
     * 22:55:12.410 [main] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - hello word
     */
    private static void join() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("start");
            sleep(3);
            log.info("end");
            return "hello word";
        });
        String result = completableFuture.join();
        log.info(result);
    }

    /**
     * @see CompletableFuture#completeExceptionally(Throwable) 当该future还没执行成功的时候， 就来获得那么就会报错
     *  导致整个future 终止
     *
     * 当future结束了 然后来调用就不会报错
     *
     */
    private static void completeExceptionally() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("start");
            sleep(3);
            log.info("end");
            return "hello word";
        });

        boolean completed = completableFuture.completeExceptionally(new RuntimeException("还没结束呢！"));
        log.info("是否结束：{}", completed);
        String result = completableFuture.join();
        log.info(result);
    }

    /**
     * 1.非级联的操作
     * 23:09:46.951 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - start
     * 23:09:49.954 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - end
     * 23:09:49.954 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - 完成：hello word
     * 23:09:49.955 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - second start
     * 23:09:51.956 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - second end
     *
     * <>
     *       CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
     *             log.info("start");
     *             sleep(3);
     *             log.info("end");
     *             return "hello word";
     *         });
     *         // 产生了一个新的future
     *         completableFuture.thenApply(str -> {
     *             log.info("second start");
     *             sleep(2);
     *             log.info("second end");
     *             return str.concat(" !!!!!!");
     *         });
     *         return completableFuture;
     * </>
     *
     * 2. 假设是级联的话 那么就会打印出最后的值：
     * 23:12:51.545 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - start
     * 23:12:54.548 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - end
     * 23:12:54.548 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - second start
     * 23:12:56.549 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - second end
     * 23:12:56.549 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - 完成：hello word !!!!!!
     * <>
     *    CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
     *             log.info("start");
     *             sleep(3);
     *             log.info("end");
     *             return "hello word";
     *         }).thenApply(str -> {
     *             log.info("second start");
     *             sleep(2);
     *             log.info("second end");
     *             return str.concat(" !!!!!!");
     *         });
     *     return completableFuture;
     * </>
     * 3.异常的操作
     * 23:16:12.032 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - start
     * 23:16:15.034 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - end
     * 23:16:15.034 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - second start
     * 23:16:17.035 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - second end
     * 23:16:17.035 [ForkJoinPool.commonPool-worker-9] INFO com.ximo.thinkingandprogramming.stage3.exectutos.CompletableFutureExample5 - 完成：java.lang.NumberFormatException: For input string: "hello word"
     *
     * @return
     */
    private static CompletableFuture<String> handleError() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("start");
            sleep(3);
            log.info("end");
            return "hello word";
        }).thenApply(str -> {
            log.info("second start");
            sleep(2);
            log.info("second end");
            return Integer.parseInt(str)+ str.concat(" !!!!!!");
            // 遇到异常的操作
        }).exceptionally(Throwable::getLocalizedMessage);
        return completableFuture;
    }

    /**
     * 强制抛出异常
     *
     */
    private static void obtrudeException() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("start");
            sleep(3);
            log.info("end");
            return "hello word";
        });

        completableFuture.obtrudeException(new RuntimeException("不可获取"));
    }



    private static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
