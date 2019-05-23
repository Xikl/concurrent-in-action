package com.ximo.thinkingandprogramming.stage3.atomic;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xikl
 * @date 2019/3/25
 */
@Slf4j
public class UnsafeApp {

    /**
     * 不能启动
     * 补充类加载器的知识
     * Exception in thread "main" java.lang.SecurityException: Unsafe
     * at sun.misc.Unsafe.getUnsafe(Unsafe.java:90)
     * at com.ximo.thinkingandprogramming.stage3.atomic.UnsafeApp.main(UnsafeApp.java:12)
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
//        Unsafe unsafe = Unsafe.getUnsafe();
//        Unsafe unsafe = getUnsafe();
//
//        System.out.println(unsafe);

        // 愚蠢的counter
        //  22:47:19.679 [main] INFO com.ximo.thinkingandprogramming.stage3.atomic.UnsafeApp - end： 995793, 耗时:73
//        execute(new StupidCounter());

        // sync
        // 22:50:30.792 [main] INFO com.ximo.thinkingandprogramming.stage3.atomic.UnsafeApp - end： 1000000, 耗时:64
//        execute(new SyncCounter());

        // 22:56:34.370 [main] INFO com.ximo.thinkingandprogramming.stage3.atomic.UnsafeApp - end： 1000000, 耗时:60
//        execute(new LockCounter());

        // 22:59:02.303 [main] INFO com.ximo.thinkingandprogramming.stage3.atomic.UnsafeApp - end： 1000000, 耗时:53
//        execute(new AtomicCounter());

        // 23:08:51.663 [main] INFO com.ximo.thinkingandprogramming.stage3.atomic.UnsafeApp - end： 1000000, 耗时:81
        execute(new MyCasCounter());



    }

    public static void execute(Counter counter) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);

        Instant start = Instant.now();
        for (int i = 0; i < 1000; i++) {
            executorService.submit(new CounterRunnable(counter, 1000));
        }

        Instant end = Instant.now();
        // 终止他
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);

        long duration = Duration.between(start, end).toMillis();
        log.info("end： {}, 耗时:{}", counter.getCounter(), duration);
    }


    /**
     * 愚蠢的记录器
     */
    static class StupidCounter implements Counter {

        private long counter = 0;


        @Override
        public void increment() {
            counter++;
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

     /** sync */
    static class SyncCounter implements Counter {

        private long counter = 0;


        @Override
        public synchronized void increment() {
            counter++;
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    /** sync */
    static class LockCounter implements Counter {

        private long counter = 0;

        /** 默认 不公平所 */
        private final Lock lock = new ReentrantLock();



        @Override
        public void increment() {
            try {
                lock.lock();
                counter++;
            } finally {
                lock.unlock();
            }
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    /**
     * 原子的 unsafe
     */
    static class AtomicCounter implements Counter {

        private AtomicLong counter = new AtomicLong();


        @Override
        public void increment() {
            counter.incrementAndGet();
        }

        @Override
        public long getCounter() {
            return counter.get();
        }

    }

    /**
     * 利用我们自己的unsafe类
     */
    static class MyCasCounter implements Counter {

        private volatile long counter = 0;
        /** unsafe */
        private Unsafe unsafe;
        /** 字段偏移量 */
        private long offset;

        public MyCasCounter() throws Exception {
            unsafe = getUnsafe();
            // getDeclaredField 获得所有的字段
            offset = unsafe.objectFieldOffset(MyCasCounter.class.getDeclaredField("counter"));
        }

        /**
         * compareAndSet这个方法主要调用unsafe.compareAndSwapInt这个方法，
         * 这个方法有四个参数，其中第一个参数为需要改变的对象，
         * 第二个为偏移量(即之前求出来的valueOffset的值)，
         * 第三个参数为期待的值，
         * 第四个为更新后的值。
         * 整个方法的作用即为若调用该方法时，value的值与expect这个值相等，
         * 那么则将value修改为update这个值，并返回一个true，
         * 如果调用该方法时，value的值与expect这个值不相等，那么不做任何操作，并返回一个false。
         */
        @Override
        public void increment() {
            long current = counter;
            // 如果预期的值不是 current 那么就会失败
            while (!unsafe.compareAndSwapLong(this, offset, current, current + 1)) {
                // 失败 我们就等于当前的值
                current = this.counter;
            }
            // 成功就结束
        }

        @Override
        public long getCounter() {
            return counter;
        }

    }

    /**
     * 获得 unsafe
     */
    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    interface Counter {
        void increment();

        long getCounter();
    }

    static class CounterRunnable implements Runnable {
        private final Counter counter;

        private final int num;

        public CounterRunnable(Counter counter, int num) {
            this.counter = counter;
            this.num = num;
        }

        @Override
        public void run() {
            for (int i = 0; i < num; i++) {
                counter.increment();
            }
        }
    }
}
