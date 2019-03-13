package com.ximo.thread.action.chapter01;

/**
 * @author 朱文赵
 * @date 2019/3/13 12:01
 */
public class SyncSeq {


    private int value;

    private synchronized int getNext() {
        return value++;
    }
}
