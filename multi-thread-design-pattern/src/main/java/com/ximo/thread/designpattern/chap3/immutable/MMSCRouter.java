package com.ximo.thread.designpattern.chap3.immutable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 朱文赵
 * @date 2018/7/10 13:55
 * @description 一个模拟彩信中心路由规则管理器的类
 * 对应的角色为ImmutableObject
 * 必须要采专用final修饰
 *
 */
public final class MMSCRouter {

    /** mmsc 路由中心 volatile 修饰 保证多线程下的可见性 但是不保证操作原子性 */
    private static volatile MMSCRouter instance = new MMSCRouter();

    private final Map<String, MMSCInfo> routerMap;

    public MMSCRouter() {
        //从数据库中加载信息
        this.routerMap = retrieveRouteMapFromDB();
    }

    private static Map<String, MMSCInfo> retrieveRouteMapFromDB() {
        //from db
        return new HashMap<>(16);
    }

    public static MMSCRouter getInstance() {
        return instance;
    }

    /**
     * 根据手机号前缀获得彩信中心信息
     *
     * @param msisdnPrefix 手机号前缀
     * @return 彩信中心信息
     */
    public MMSCInfo getMMSC(String msisdnPrefix) {
        return routerMap.get(msisdnPrefix);
    }

    /**
     * 设置新的实例
     *
     * @param newInstance 新的彩信中心
     */
    public static void setInstance(MMSCRouter newInstance) {
        instance = newInstance;
    }

    /**
     * 深度拷贝
     *
     * @param map 存放彩信信息的map
     * @return 深度拷贝的彩信信息
     */
    public static Map<String, MMSCInfo> deepCopy(Map<String, MMSCInfo> map) {
        Map<String, MMSCInfo> result = new HashMap<>(16);
        map.forEach((key, value) -> result.put(key, new MMSCInfo(value)));
        return result;
    }

    /**
     * 做防御性的复制给别人调用 也就是复制一份副本给别人调用
     *
     * @return routerMap的拷贝
     */
    public Map<String, MMSCInfo> getRouterMap() {
        return Collections.unmodifiableMap(deepCopy(routerMap));
    }

}
