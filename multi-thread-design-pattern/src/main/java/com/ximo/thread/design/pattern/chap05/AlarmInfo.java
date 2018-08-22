package com.ximo.thread.design.pattern.chap05;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * @author 朱文赵
 * @date 2018/8/13 12:18
 * @description
 */
public class AlarmInfo {

    private String alarmId;

    private String extraInfo;

    private final AlarmType alarmType;

    public AlarmInfo(String alarmId, AlarmType alarmType) {
        this.alarmId = alarmId;
        this.alarmType = alarmType;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlarmInfo)) {
            return false;
        }
        AlarmInfo alarmInfo = (AlarmInfo) o;
        return Objects.equals(alarmId, alarmInfo.alarmId) &&
                Objects.equals(extraInfo, alarmInfo.extraInfo) &&
                alarmType == alarmInfo.alarmType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(alarmId, extraInfo, alarmType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("alarmId", alarmId)
                .append("extraInfo", extraInfo)
                .append("alarmType", alarmType)
                .toString();
    }
}
