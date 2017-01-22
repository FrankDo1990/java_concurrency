package com.frank.concurrency.combination;

import com.frank.concurrency.annotations.GuardedBy;
import com.frank.concurrency.annotations.ThreadSafe;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

/**
 * 将可变的状态@MutablePoint类封装在 final类型中，所有访问locations的路径都采用同一个锁（@GuardedBy("this")）,
 * 返回locations对象时都是copy。
 * Created by dufeng on 2017/1/20.
 */
@ThreadSafe
public class MonitorVehicleTracker {
    @GuardedBy("this")
    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    public synchronized void setLocation(String id , int x , int y){
        MutablePoint m = locations.get(id);
        if (m == null){
            throw new IllegalArgumentException("id not exists");
        }
        m.setX(x);
        m.setY(y);
    }

    public synchronized MutablePoint getLocation(String id){
        MutablePoint m = locations.get(id);
        if (m == null){
            throw new IllegalArgumentException("id not exists");
        }
        return new MutablePoint(m.getX(), m.getY());
    }

    public synchronized Map<String, MutablePoint> getLocations(){
        return deepCopy(locations);
    }
    private Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m){
        Map<String, MutablePoint> res = Maps.newHashMap();
        for (String id : m.keySet()){
            MutablePoint mutablePoint = m.get(id);
            res.put(id, new MutablePoint(mutablePoint.getX(), mutablePoint.getY()));
        }
        return Collections.unmodifiableMap(res);
    }
}
