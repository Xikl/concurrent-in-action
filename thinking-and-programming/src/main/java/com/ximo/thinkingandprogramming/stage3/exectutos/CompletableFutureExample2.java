package com.ximo.thinkingandprogramming.stage3.exectutos;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author xikl
 * @date 2019/4/3
 */
public class CompletableFutureExample2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFutureExample2.supplyAsync();

//        runAsync();

//        completedFuture("datatat");
//        anyOf();

//        allOf();

        create();
    }


    /**
     * obj: start
     * str: start
     * str: hello word
     * obj: java.lang.Object@4609e439
     * done
     *
     * @see java.util.concurrent.CompletableFuture#supplyAsync(Supplier)
     */
    private static void supplyAsync() {
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(Object::new)
                .thenAcceptAsync(data -> {
                    System.out.println("obj: start");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("obj: " + data);
                }).runAfterBoth(CompletableFuture.supplyAsync(() -> "hello word")
                                .thenAcceptAsync(str -> {
                                    System.out.println("str: start");
                                    try {
                                        TimeUnit.SECONDS.sleep(3);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("str: " + str);
                                }),
                        // Runnable action 结束之后需要做的事情
                        () -> System.out.println("done"));

        completableFuture.join();

    }

    /**
     * obj: start
     * obj: end
     * done
     *
     */
    private static void runAsync() {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("obj: start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("obj: end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).whenComplete((result, error) -> System.out.println("done"));
        completableFuture.join();
    }


    private static void completedFuture(String data) {
        CompletableFuture<Void> completableFuture =
                CompletableFuture.completedFuture(data).thenAccept(System.out::println);
        completableFuture.join();
    }

    /**
     * 拿到任意一个值 但是都是执行的
     * 1 over: data1
     * 2 over: data2
     * data1
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void anyOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture1 =
                CompletableFuture.supplyAsync(() -> "data1").whenComplete((v, e) -> System.out.println("1 over: " + v));
        CompletableFuture<String> completableFuture2 =
                        CompletableFuture.supplyAsync(() -> "data2").whenComplete((v, e) -> System.out.println("2 over: " + v));

        CompletableFuture<Object> finalFuture = CompletableFuture.anyOf(completableFuture1, completableFuture2);
        System.out.println(finalFuture.get());
    }

    /**
     * 一起去执行 相当于一个容器
     * 1 over: data1
     * 2 over: data2
     *
     * 没有返回值
     *
     */
    private static void allOf() {
        CompletableFuture<String> completableFuture1 =
                CompletableFuture.supplyAsync(() -> "data1").whenComplete((v, e) -> System.out.println("1 over: " + v));
        CompletableFuture<String> completableFuture2 =
                CompletableFuture.supplyAsync(() -> "data2").whenComplete((v, e) -> System.out.println("2 over: " + v));


        CompletableFuture<Void> finalFuture = CompletableFuture.allOf(completableFuture1, completableFuture2);
        finalFuture.join();

    }

    private static void create() {
        CompletableFuture<String> stringCompletableFuture = new CompletableFuture<>();
        Thread t = new Thread(() -> {
            stringCompletableFuture.complete("data");
            System.out.println("data");
        });
        t.start();

        stringCompletableFuture.whenComplete((v, r) -> System.out.println(v));
        stringCompletableFuture.join();
    }

}
