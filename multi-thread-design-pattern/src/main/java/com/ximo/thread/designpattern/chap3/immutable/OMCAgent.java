package com.ximo.thread.designpattern.chap3.immutable;

/**
 * @author 朱文赵
 * @date 2018/7/10 15:46
 * @description
 */
public class OMCAgent extends Thread {

    @Override
    public void run() {
        boolean isTableModifiedMsg = false;
        String updatedTableName = null;
        while (true) {
            //获得连接后
            //重置彩信中心信息
            if (isTableModifiedMsg) {
                if ("MMSCInfo".equals(updatedTableName)) {
                    MMSCRouter.setInstance(new MMSCRouter());
                }
            }
        }



    }
}
