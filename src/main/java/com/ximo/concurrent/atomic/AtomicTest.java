package com.ximo.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author 朱文赵
 * @date 2018/6/17
 * @description
 */
public class AtomicTest {

    private static AtomicInteger largest = new AtomicInteger();
    private static LongAdder adder = new LongAdder();
    private static LongAccumulator accumulator = new LongAccumulator(Long::sum, 0);
    private static Integer observed = 1_000;

    /**
     * 更新原子变量，进行任意数学操作
     *
     */
    private static int updateAndGet() {
        return largest.updateAndGet(x -> Math.max(x, observed));
    }

    /**
     * 通过计算largest和observer的值通过Math.max来比较
     *
     * @return 二者的最大值
     */
    private static int accumulateAndGet() {
        return largest.accumulateAndGet(observed, Math::max);
    }

    private static void adder() {
        adder.add(10);
        adder.increment();
    }

    private static long getAdderSum() {
        return adder.sum();
    }

    private static long accumulate() {
        accumulator.accumulate(10);
        return accumulator.get();
    }

}
