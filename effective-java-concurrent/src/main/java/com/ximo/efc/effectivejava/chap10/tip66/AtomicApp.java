package com.ximo.efc.effectivejava.chap10.tip66;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 朱文赵
 * @date 2019/3/13 15:40
 */
public class AtomicApp {

    private static volatile long nextSerialNumber = 0;
    /**
     * Non-atomic operation on volatile field 'nextSerialNumber' less... (Ctrl+F1)
     * Inspection info: Reports any non-atomic operations on volatile fields. Non-atomic operations on volatile fields are operations where the field is read and the value is used to update the field. It is possible for the value of the field to change between the read and the write, possibly invalidating the operation. The non-atomic operation can be avoided by surrounding it with a synchronized block or by making use of one of the classes from the java.util.concurrent.atomic package.
     * 该方法会提示非原子操作
     * 其他线程可能会看到前一个线程修改期间的值 成为安全失败
     * 有两个解决办法
     * @return
     */
    private static long getAndIncrement() {
        return nextSerialNumber++;
    }

    /** 加同步 */
    private static long nextSerialNumber2 = 0;

    private static synchronized long getAndIncrement2() {
        return nextSerialNumber++;
    }

    /** 使用原子类 */
    private static AtomicLong nextSerialNumber3 = new AtomicLong(0);

    private static synchronized long getAndIncrement3() {
        return nextSerialNumber3.getAndIncrement();
    }


    public static void main(String[] args) {
        System.out.println(new AtomicLong().get());

    }


}
