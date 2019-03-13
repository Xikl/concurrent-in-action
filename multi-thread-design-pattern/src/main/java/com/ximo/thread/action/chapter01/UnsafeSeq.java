package com.ximo.thread.action.chapter01;

/**
 * @author 朱文赵
 * @date 2019/3/13 11:56
 */
public class UnsafeSeq {

    private int value;

    private int getNext() {
        return value++;
    }


}
