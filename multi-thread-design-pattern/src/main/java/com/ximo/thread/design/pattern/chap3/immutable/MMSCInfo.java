package com.ximo.thread.design.pattern.chap3.immutable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author 朱文赵
 * @date 2018/7/10 14:05
 * @description 彩信信息中心信息 不可变对象
 * immutableObject
 */
@AllArgsConstructor
@Getter
@ToString
public final class MMSCInfo {

    private final String deviceId;

    private final String url;

    private final Integer maxAttachmentSizeInBytes;

    public MMSCInfo(MMSCInfo protoType) {
        this.deviceId = protoType.getDeviceId();
        this.url = protoType.getUrl();
        this.maxAttachmentSizeInBytes = protoType.getMaxAttachmentSizeInBytes();
    }





}
