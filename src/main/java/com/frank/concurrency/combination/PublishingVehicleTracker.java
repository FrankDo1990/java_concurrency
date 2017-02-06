package com.frank.concurrency.combination;

/**
 * Created by dufeng on 2017/1/20.
 */

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 安全发布PublishingVehicleTracker
 * 因为无不变性限制，所以可以将PublishingVehicleTracker 的线程安全性委托给ConcurrentHashMap 以及 map中的element ThreadSafePoint对象
 */
public class PublishingVehicleTracker {
    private final ConcurrentMap<String, ThreadSafePoint> locations;
    private final Map<String, ThreadSafePoint> unmodified;

    public PublishingVehicleTracker(ConcurrentMap<String, ThreadSafePoint> locations) {
        this.locations = new ConcurrentHashMap<String, ThreadSafePoint>(locations);
        unmodified = Collections.unmodifiableMap(this.locations);
    }

    public void setLocation(String id, long x, long y){
        if (!locations.containsKey(id)){
            throw new IllegalArgumentException("illegal id " + id);
        }
        locations.replace(id, new ThreadSafePoint(x, y));
    }

    public void getLocation(String id){
        locations.get(id);
    }

    public Map<String, ThreadSafePoint> getLocations(){
        return unmodified;
    }

}
