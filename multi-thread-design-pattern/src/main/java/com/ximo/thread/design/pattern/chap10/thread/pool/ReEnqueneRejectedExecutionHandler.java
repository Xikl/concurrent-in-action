package com.ximo.thread.design.pattern.chap10.thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 朱文赵
 * @date 2018/7/18 11:54
 * @description
 */
@Slf4j
public class ReEnqueneRejectedExecutionHandler implements RejectedExecutionHandler {


    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (executor.isShutdown()) {
            return;
        }
        try {
            //可以获得当前线程池的一些状态值
            int largestPoolSize = executor.getLargestPoolSize();
            int poolSize = executor.getPoolSize();

            executor.getQueue().put(r);
        } catch (InterruptedException e) {
            log.info("线程被阻断", e);
        }
    }
}
