package com.ximo.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 朱文赵
 * @date 2018/6/17
 * @description
 */
public class LockTest {

    private ReentrantLock myLock;
    private Condition myCondition;
    /**  读写锁 */
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    private void testLock(boolean tags) {
        try {
            if (myLock.tryLock(1_000, TimeUnit.MILLISECONDS)) {
                if (tags) {
                    myCondition.await(100, TimeUnit.MILLISECONDS);
                }
                try {
                    System.out.println("锁");
                } finally {
                    myLock.unlock();
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private Lock readLock() {
        return reentrantReadWriteLock.readLock();
    }

    private Lock writeLock() {
        return reentrantReadWriteLock.writeLock();
    }
    /** 对读方法进行加锁  */
    private int getTotal() {
        readLock().lock();
        try{
            //do something
            return 1000;
        }finally {
            readLock().unlock();
        }
    }

    /**  对所有的写操作 进行加读锁*/
    private void add() {
        writeLock().lock();
        try{
            //do something

        }finally {
            writeLock().unlock();
        }
    }


}
