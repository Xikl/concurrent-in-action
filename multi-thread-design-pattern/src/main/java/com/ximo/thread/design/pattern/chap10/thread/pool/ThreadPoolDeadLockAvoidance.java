package com.ximo.thread.design.pattern.chap10.thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author 朱文赵
 * @date 2018/7/19 12:10
 * @description
 */
@Slf4j
public class ThreadPoolDeadLockAvoidance {

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 1, 60, TimeUnit.SECONDS,
            new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
        ThreadPoolDeadLockAvoidance avoidance = new ThreadPoolDeadLockAvoidance();

        avoidance.test("test message");

    }

    public void test(final String message) {
        Runnable taskA = () -> {
            log.info("task is executing");
            Runnable taskB = () -> log.info("taskB processing" + message);
            Future<?> submit = executor.submit(taskB);

            try {
                submit.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("异常", e);
            }
            log.info("taskA done");
        };
        executor.submit(taskA);
    }


}
