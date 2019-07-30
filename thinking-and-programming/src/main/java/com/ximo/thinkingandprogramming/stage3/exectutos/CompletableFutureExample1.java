package com.ximo.thinkingandprogramming.stage3.exectutos;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * CompletableFuture 的学习
 *
 * @author xikl
 * @date 2019/4/2
 */
@Slf4j
public class CompletableFutureExample1 {

    public static void main(String[] args) throws InterruptedException {
//        CompletableFutureExample1.runWithCompletableFuture();

//        CompletableFutureExample1.runWithMultiTaskByFuture();
        CompletableFutureExample1.runWithMultiTaskByCompletableFuture();
    }


    private static void runWithFuture() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<?> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        while (!future.isDone()) {

        }
        System.out.println("DONE");
    }

    /**
     * runAsync
     * whenComplete
     *
     *
     */
    private static void runWithCompletableFuture() throws InterruptedException {
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 当完成的时候
        }).whenComplete((result, error) -> {
            System.out.println(result);
        });
        System.out.println("done");

        Thread thread = Thread.currentThread();
        System.out.println(thread.getName());
        thread.join();

    }

    /**
     * get:pool-1-thread-9will sleep5
     * get:pool-1-thread-7will sleep19
     * get:pool-1-thread-4will sleep1
     * get:pool-1-thread-6will sleep18
     * get:pool-1-thread-8will sleep3
     * get:pool-1-thread-5will sleep11
     * get:pool-1-thread-10will sleep19
     * get:pool-1-thread-2will sleep0
     * get:pool-1-thread-2sleep done, get the data0
     * get:pool-1-thread-1will sleep9
     * get:pool-1-thread-3will sleep14
     * get:pool-1-thread-4sleep done, get the data1
     * get:pool-1-thread-8sleep done, get the data3
     * get:pool-1-thread-9sleep done, get the data5
     * get:pool-1-thread-1sleep done, get the data9
     * get:pool-1-thread-5sleep done, get the data11
     * get:pool-1-thread-3sleep done, get the data14
     * get:pool-1-thread-6sleep done, get the data18
     * get:pool-1-thread-10sleep done, get the data19
     * get:pool-1-thread-7sleep done, get the data19
     * display:mainwill sleep 19
     * display:ForkJoinPool.commonPool-worker-9will sleep 14
     * display:ForkJoinPool.commonPool-worker-11will sleep 18
     * display:ForkJoinPool.commonPool-worker-4will sleep 3
     * display:ForkJoinPool.commonPool-worker-13will sleep 19
     * display:ForkJoinPool.commonPool-worker-2will sleep 5
     * display:ForkJoinPool.commonPool-worker-6will sleep 0
     * display:ForkJoinPool.commonPool-worker-6sleep done, display the data:0
     * display:ForkJoinPool.commonPool-worker-8will sleep 9
     * display:ForkJoinPool.commonPool-worker-6will sleep 1
     * display:ForkJoinPool.commonPool-worker-15will sleep 11
     * display:ForkJoinPool.commonPool-worker-6sleep done, display the data:1
     * display:ForkJoinPool.commonPool-worker-4sleep done, display the data:3
     * display:ForkJoinPool.commonPool-worker-2sleep done, display the data:5
     * display:ForkJoinPool.commonPool-worker-8sleep done, display the data:9
     * display:ForkJoinPool.commonPool-worker-15sleep done, display the data:11
     * display:ForkJoinPool.commonPool-worker-9sleep done, display the data:14
     * display:ForkJoinPool.commonPool-worker-11sleep done, display the data:18
     * display:mainsleep done, display the data:19
     * display:ForkJoinPool.commonPool-worker-13sleep done, display the data:19
     *
     * @throws InterruptedException
     */
    private static void runWithMultiTaskByFuture() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Integer>> taskList = IntStream.range(0, 10)
                .boxed().map(i -> (Callable<Integer>) () -> get()).collect(toList());

        executorService.invokeAll(taskList).stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            // 并行foreach
        }).parallel().forEach(CompletableFutureExample1::display);
    }

    /**
     * 先执行完 先done
     *
     * get:ForkJoinPool.commonPool-worker-9will sleep8
     * get:ForkJoinPool.commonPool-worker-2will sleep19
     * get:ForkJoinPool.commonPool-worker-11will sleep14
     * get:ForkJoinPool.commonPool-worker-4will sleep14
     * get:ForkJoinPool.commonPool-worker-13will sleep14
     * get:ForkJoinPool.commonPool-worker-6will sleep18
     * get:ForkJoinPool.commonPool-worker-15will sleep2
     * get:ForkJoinPool.commonPool-worker-8will sleep17
     * get:ForkJoinPool.commonPool-worker-1will sleep11
     * get:ForkJoinPool.commonPool-worker-10will sleep17
     * get:ForkJoinPool.commonPool-worker-15sleep done, get the data2
     * done:ForkJoinPool.commonPool-worker-15done2
     * get:ForkJoinPool.commonPool-worker-9sleep done, get the data8
     * done:ForkJoinPool.commonPool-worker-9done8
     * get:ForkJoinPool.commonPool-worker-1sleep done, get the data11
     * done:ForkJoinPool.commonPool-worker-1done11
     * get:ForkJoinPool.commonPool-worker-13sleep done, get the data14
     * done:ForkJoinPool.commonPool-worker-13done14
     * get:ForkJoinPool.commonPool-worker-11sleep done, get the data14
     * get:ForkJoinPool.commonPool-worker-4sleep done, get the data14
     * done:ForkJoinPool.commonPool-worker-4done14
     * done:ForkJoinPool.commonPool-worker-11done14
     * get:ForkJoinPool.commonPool-worker-10sleep done, get the data17
     * get:ForkJoinPool.commonPool-worker-8sleep done, get the data17
     * done:ForkJoinPool.commonPool-worker-8done17
     * done:ForkJoinPool.commonPool-worker-10done17
     * get:ForkJoinPool.commonPool-worker-6sleep done, get the data18
     * done:ForkJoinPool.commonPool-worker-6done18
     * get:ForkJoinPool.commonPool-worker-2sleep done, get the data19
     * done:ForkJoinPool.commonPool-worker-2done19
     *
     * @throws InterruptedException
     */
    private static void runWithMultiTaskByCompletableFuture() throws InterruptedException {
        IntStream.range(0, 10).boxed()
                .forEach(i -> CompletableFuture.supplyAsync(CompletableFutureExample1::get)
//                        .thenAccept(CompletableFutureExample1::display)
                .whenComplete((result, error) -> log.info("done: {} done {}", Thread.currentThread().getName(), result)));
        Thread.currentThread().join();
    }


    private static void display(Integer data) {
        log.info("display: will sleep {}", data);
        try {
            TimeUnit.SECONDS.sleep(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("display: sleep done, display the data:{}", data);
    }

    private static int get() {
        int value = ThreadLocalRandom.current().nextInt(20);
        try {
            log.info("get: will sleep {}",  value);
            TimeUnit.SECONDS.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("get: sleep done, get the data {}", value);
        return value;
    }

}