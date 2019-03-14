package com.ximo.efc.effectivejava.chap10.tip69;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 朱文赵
 * @date 2019/3/14 9:29
 */
public class ConcurrentMapApp {

    private static ConcurrentMap<String, String> concurrentMap = new ConcurrentHashMap<>();

    /**
     * 返回内部方法
     *
     * @param str
     * @return
     */
    private static String intern(String str) {
        final String prevValue = concurrentMap.putIfAbsent(str, str);
        return prevValue == null ? str : prevValue;
    }

    /** 优化后的操作 先 get 再判断putIfAbsent */
    private static String intern2(String str) {
        String result = concurrentMap.get(str);
        if (result == null) {
            result = concurrentMap.putIfAbsent(str, str);
            if (result == null) {
                result = str;
            }
        }
        return result;
    }


}
