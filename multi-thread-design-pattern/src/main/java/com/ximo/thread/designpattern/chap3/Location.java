package com.ximo.thread.designpattern.chap3;

import lombok.Getter;

/**
 * @author 朱文赵
 * @date 2018/7/10 12:15
 * @description
 */
@Getter
public class Location {

    private final double x;
    private final double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //线程不安全
//    public void setXY(double x, double y) {
//        this.x = x;
//        this.y = y;
//    }
}
