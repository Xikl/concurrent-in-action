package com.ximo.thread.design.pattern.chap3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 朱文赵
 * @date 2018/7/10 12:18
 * @description
 */
public class VehicleTracker {

    private Map<String, Location> locationMap = new ConcurrentHashMap<>();

    public void updateLocation(String vehicleId, Location newLocation) {
        locationMap.put(vehicleId, newLocation);
    }


}
