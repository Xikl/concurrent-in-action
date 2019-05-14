package com.ximo.thinkingandprogramming.stage3.the7conucy.chap02;

/**
 * @author xikl
 * @date 2019/5/14
 */
public class Counting {

    static class Counter {
        private int count = 0;

        public void increment() {
            ++count;
        }

        public int getCount() {
            return count;
        }

    }


    static class CountingThread extends Thread {

        private Counter counter;

        public CountingThread(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        CountingThread countingThread1 = new CountingThread(counter);
        CountingThread countingThread2 = new CountingThread(counter);

        countingThread1.start();
        countingThread2.start();
        countingThread1.join();
        countingThread2.join();

        System.out.println(counter.getCount());
    }
}


