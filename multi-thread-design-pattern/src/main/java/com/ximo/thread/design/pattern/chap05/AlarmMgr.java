package com.ximo.thread.design.pattern.chap05;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 朱文赵
 * @date 2018/8/6 12:26
 * @description
 */
@Slf4j
public class AlarmMgr {

    /** 是否需要关闭 */
    private volatile boolean shutdownRequested = false;

    private final AlarmSendingThread alarmSendingThread;
    /** 私有构造 */
    private AlarmMgr() {
        alarmSendingThread = new AlarmSendingThread();
    }

    /** 单例模式 holder */
    private static class AlarmMgrInstanceHolder {
        private static final AlarmMgr ALARM_MGR_INSTANCE = new AlarmMgr();
    }

    /** 获得实例 */
    public static AlarmMgr getInstance() {
        return AlarmMgrInstanceHolder.ALARM_MGR_INSTANCE;
    }

    /**
     * 告警
     *
     * @param alarmType 告警类型
     * @param alarmId 告警ID
     * @param extraInfo 额外的信息
     */
    public void execAlarm(AlarmType alarmType, String alarmId, String extraInfo) {
        log.info("告警类型{}，告警Id:{},额外的信息：{}", alarmType, alarmId, extraInfo);
        AlarmInfo alarmInfo = new AlarmInfo(alarmId, alarmType);
        alarmInfo.setExtraInfo(extraInfo);
        int duplicateSubmissionCount = 0;



    }






}
