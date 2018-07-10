package com.ximo.concurrent.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author 朱文赵
 * @date 2018/7/5 12:03
 * @description
 */
public class PcDataFactory implements EventFactory<PcData> {

    @Override
    public PcData newInstance() {
        return new PcData();
    }

}
