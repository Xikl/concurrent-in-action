package com.ximo.thinkingandprogramming.stage3.exectutos;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 接着学习CompletableFuture的方法
 *
 * @author xikl
 * @date 2019/4/9
 */
@Slf4j
public class CompletableFutureExample3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        whenCompleteAsync();
//        thenApply();
        handleAsync();
    }


    /**
     * 当结束的时候进行调用
     *
     * @throws ExecutionException
     * @throws InterruptedException
     * @see CompletableFuture#whenComplete(BiConsumer)
     * @see CompletableFuture#whenCompleteAsync(BiConsumer)
     */
    private static void whenCompleteAsync() throws ExecutionException, InterruptedException {

        CompletableFuture<String> future =
                CompletableFuture.supplyAsync(() -> "hello CompletableFuture");

        future.whenCompleteAsync((v, e) -> System.out.println("done, get value" + v));


        System.out.println(future.get());
    }

    /**
     * @throws ExecutionException
     * @throws InterruptedException
     * @see CompletableFuture#thenApply(Function)
     */
    private static void thenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future =
                CompletableFuture.supplyAsync(() -> "hello CompletableFuture")
                        .thenApply(String::length);

        System.out.println(future.get());
    }

    /**
     * 没有异常的时候 e 为 null 所以 要用 ofNullable
     * @see CompletableFuture#handleAsync(BiFunction)
     */
    private static void handleAsync() {
        CompletableFuture<Integer> future =
                CompletableFuture.supplyAsync(() -> "hello c")
                        .thenApply(str -> {
                            if (str.length() == 5) {
                                throw new UnsupportedOperationException("太短了");
                            }
                            return str.length();
                        }).handleAsync((v, e) -> {
                            // 处理异常
                    Optional.ofNullable(e).ifPresent(t -> log.error("error", t));
                    System.out.println(v);
                    return v;
                });
        System.out.println(future.join());
    }


}
