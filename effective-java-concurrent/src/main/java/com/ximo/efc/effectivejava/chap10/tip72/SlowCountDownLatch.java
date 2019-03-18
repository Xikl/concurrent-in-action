package com.ximo.efc.effectivejava.chap10.tip72;

/**
 * @author xikl
 * @date 2019/3/18
 */
public class SlowCountDownLatch {

    private int count;

    public SlowCountDownLatch(int count) {
        if (count < 0) {
            throw new IllegalArgumentException();
        }
        this.count = count;
    }

    public void await() {
        while (true) {
            synchronized (this) {
                if (count == 0) {
                    return;
                }
            }
        }

    }

    /**
     * 慢一千倍
     *
     */
    public synchronized void countDown() {
        if (count != 0) {
            count--;
        }
    }

}
