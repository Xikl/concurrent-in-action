package com.ximo.thread.design.pattern.chap05;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 朱文赵
 * @date 2018/8/7 11:58
 * @description
 */
public abstract class AbstractTerminableThread extends Thread implements Terminatable {

    private final TerminationToken terminationToken;

    public AbstractTerminableThread() {
        this(new TerminationToken());
    }

    public AbstractTerminableThread(TerminationToken terminationToken) {
        this.setDaemon(true);
        this.terminationToken = terminationToken;
        terminationToken.register(this);
    }

    protected abstract void doRun();

    protected abstract void doCleanup(Exception cause);

    protected void doTerminate() {
        //do nothing
    }

    @Override
    public void terminate() {
        terminationToken.setToShutdown(true);
        try {
            doTerminate();
        } finally{
            //没有任务需要执行的时候 调用父类的终止方法进行结束
            if (terminationToken.reservations.get() <= 0) {
                super.interrupt();
            }
        }
    }

    @Override
    public void interrupt() {
        terminate();
    }


    @Override
    public void run() {
        Exception exception = null;
        try {
            for (; ; ) {
                boolean shutdownAble = terminationToken.isToShutdown() && terminationToken.reservations.get() <= 0;
                if (shutdownAble) {
                    break;
                }
                //执行相应的业务
                doRun();
            }
        } catch (Exception e) {
            exception = e;
        }finally {
            try {
                doCleanup(exception);
            } finally {
                terminationToken.notifyThreadTermination(this);
            }

        }
    }

    /**
     * 是否等待线程结束才终止
     *
     * @param waitUtilThreadTerminated 是否等待结束
     */
    public void terminate(boolean waitUtilThreadTerminated) {
        terminate();
        if (waitUtilThreadTerminated) {
            try {
                this.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }



}
