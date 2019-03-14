package com.ximo.efc.effectivejava.chap10.tip69;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 朱文赵
 * @date 2019/3/14 10:21
 */
public class BlockingQueueApp {

    private static LinkedBlockingQueue<String> queue
            = new LinkedBlockingQueue<>(Stream.of("aa", "bb", "cc", "dd", "ee", "ff").collect(Collectors.toList()));



    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        Runnable task = () -> {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < 6; i++) {
            executorService.submit(task);
        }
        executorService.shutdown();
    }


}
