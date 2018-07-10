package com.ximo.concurrent.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author 朱文赵
 * @date 2018/7/5 12:04
 * @description
 */
public class Producer {

    private final RingBuffer<PcData> pcDataRingBuffer;

    public Producer(RingBuffer<PcData> pcDataRingBuffer) {
        this.pcDataRingBuffer = pcDataRingBuffer;
    }

    /**
     * 获得byteBuffer中数据 并且转载到环形缓冲区中
     *
     * @param byteBuffer
     */
    public void pushData(ByteBuffer byteBuffer) {
        long nextSequence = pcDataRingBuffer.next();
        PcData pcData = pcDataRingBuffer.get(nextSequence);
        pcData.setValue(byteBuffer.getLong(0));
        //发布数据 告诉消费者
        pcDataRingBuffer.publish(nextSequence);
    }
}
