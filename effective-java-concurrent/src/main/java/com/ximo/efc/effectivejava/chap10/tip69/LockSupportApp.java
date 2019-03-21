package com.ximo.efc.effectivejava.chap10.tip69;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author 朱文赵
 * @date 2019/3/15 11:15
 */
@Slf4j
public class LockSupportApp {

    private static final Object object = new Object();

    public static class ChangeObjectThread extends Thread {

        public ChangeObjectThread(String threadName) {
            super(threadName);
        }

        /**
         * {@link LockSupport#park()} 方法能够阻塞当前线程，
         * 类似的还有{@link LockSupport#parkNanos(long)} {@link LockSupport#parkUntil(long)} 他们实现了限时等待
         *
         */
        @Override
        public void run() {
            synchronized (object) {
                System.out.println("当前线程名" + getName());
                // 阻塞当前线程
                LockSupport.park();
                if (Thread.interrupted()) {
                    log.info("当前线程：" + getName() + "被中断");
                }
            }
        }
    }

    /**
     * unpack方法和pack方法 采用一种信号量的机制
     * 正是这种机制 让即便unpack方法优先于pack方法运行线程也能正常退出
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread t1 = new ChangeObjectThread("线程1");
        ChangeObjectThread t2 = new ChangeObjectThread("线程2");

        t1.start();
        Thread.sleep(100);
        t2.start();
//        LockSupport.unpark(t1);
        t1.interrupt();
        LockSupport.unpark(t2);
        t1.join();
        t2.join();

    }


}
