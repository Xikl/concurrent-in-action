package com.ximo.concurrent.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * @author 朱文赵
 * @date 2018/7/4 12:27
 * @description
 */
public class Consumer implements WorkHandler<PcData> {

    @Override
    public void onEvent(PcData pcData) throws Exception {
        System.out.println(Thread.currentThread().getId() + "Event:--" + pcData.getValue() * pcData.getValue() + "--");
    }
}
