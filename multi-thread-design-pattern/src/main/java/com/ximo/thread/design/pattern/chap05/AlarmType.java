package com.ximo.thread.design.pattern.chap05;

/**
 * @author 朱文赵
 * @date 2018/8/13 12:12
 * @description 告警类型
 */
public enum AlarmType {

    /** 出现故障 */
    FAULT("fault"),
    /** 重启 */
    RESUME("resume"),
    ;

    private final String name;

    AlarmType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
