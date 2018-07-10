package com.ximo.thread.design.pattern.chap1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author 朱文赵
 * @date 2018/7/10 11:13
 * @description
 */
public class WorkerThread {

    public static void main(String[] args) {
        Helper helper = new Helper();

        helper.init();
        helper.submit("test task");

    }

    static class Helper {
        private final BlockingQueue<String> workQueue = new ArrayBlockingQueue<>(100);

        private final Thread workThread = new Thread(() -> {
            String task = null;
            while (true) {
                try {
                    task = workQueue.take();
                } catch (InterruptedException e) {
                    break;
                }
            }

        });

        public void init() {
            workThread.start();
        }

        protected String doProcess(String task) {
            return task + "-> processed";
        }

        public void submit(String task) {
            try {
                workQueue.put(task);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }




}
