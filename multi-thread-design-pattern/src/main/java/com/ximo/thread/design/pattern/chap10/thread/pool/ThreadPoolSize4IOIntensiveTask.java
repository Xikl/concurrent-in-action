package com.ximo.thread.design.pattern.chap10.thread.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 朱文赵
 * @date 2018/7/17 12:18
 * @description
 */
@Slf4j
public class ThreadPoolSize4IOIntensiveTask {

    public static void main(String[] args) {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1,
                Runtime.getRuntime().availableProcessors() * 2,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1024),
                new ThreadFactoryBuilder().setDaemon(true).setNameFormat("io-intensive-task-pool-%d").build());

        threadPoolExecutor.execute(() -> {
            log.info("any io intensive task ");
            log.info("start");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                log.error("异常", e);
            }
            log.info("end");
        });
    }

}
