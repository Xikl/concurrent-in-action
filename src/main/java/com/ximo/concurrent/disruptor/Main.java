package com.ximo.concurrent.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author 朱文赵
 * @date 2018/7/5 12:12
 * @description
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Executor executor = Executors.newCachedThreadPool();

        PcDataFactory pcDataFactory = new PcDataFactory();

        int bufferSize = 1024;
        Disruptor<PcData> pcDataDisruptor = new
                Disruptor<>(pcDataFactory, bufferSize, executor, ProducerType.MULTI, new BlockingWaitStrategy());
        pcDataDisruptor.handleEventsWithWorkerPool(new Consumer(), new Consumer(), new Consumer(), new Consumer());
        pcDataDisruptor.start();

        RingBuffer<PcData> ringBuffer = pcDataDisruptor.getRingBuffer();
        Producer producer = new Producer(ringBuffer);

        ByteBuffer allocate = ByteBuffer.allocate(8);
        for (int i = 0; true; i++) {
            allocate.putLong(0, 1);
            producer.pushData(allocate);
            Thread.sleep(1000);
            System.out.println("add data " + 1);

        }


    }


}
