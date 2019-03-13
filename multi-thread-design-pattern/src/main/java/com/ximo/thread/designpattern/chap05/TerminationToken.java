package com.ximo.thread.designpattern.chap05;

import java.lang.ref.WeakReference;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 朱文赵
 * @date 2018/8/7 12:03
 * @description
 */
public class TerminationToken {

    protected volatile boolean toShutdown = false;

    /** 有需要执行的任务数 */
    public final AtomicInteger reservations = new AtomicInteger(0);

    /**
     * 协调的线程 泛型为弱引用的可终止的线程
     */
    private final Queue<WeakReference<Terminatable>> coordinatedThreads;

    public TerminationToken() {
        this.coordinatedThreads = new ConcurrentLinkedQueue<>();
    }

    public boolean isToShutdown() {
        return toShutdown;
    }

    public void setToShutdown(boolean toShutdown) {
        this.toShutdown = toShutdown;
    }

    protected void register(Terminatable thread) {
        coordinatedThreads.add(new WeakReference<>(thread));
    }

    /**
     * 通知可停止的线程中的可以停止了
     *
     * @param thread 已停止的线程
     */
    protected void notifyThreadTermination(Terminatable thread) {
        WeakReference<Terminatable> wrThread;
        Terminatable otherThread;
        while ((wrThread = coordinatedThreads.poll()) != null) {
            otherThread = wrThread.get();
            if (otherThread != null && otherThread != thread) {
                otherThread.terminate();
            }
        }
    }

}
