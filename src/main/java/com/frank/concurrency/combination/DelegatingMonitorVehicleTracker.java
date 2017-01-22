package com.frank.concurrency.combination;

import com.frank.concurrency.annotations.Immutable;
import com.frank.concurrency.annotations.ThreadSafe;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by dufeng on 2017/1/20.
 */

/**
 * 使用ImmutablePoint代替MutablePoint，保持对locations不发布，发布的是unModified.
 * 同时使用ImmutablePoint可以保证locations对象的线程安全性
 * 将线程安全委托到ConcurrentMap和ImmutablePoint对象中
 */
@ThreadSafe
public class DelegatingMonitorVehicleTracker {

    private final ConcurrentMap<String, ImmutablePoint> locations;
    private final Map<String, ImmutablePoint> unModified;

    public DelegatingMonitorVehicleTracker(ConcurrentMap<String, ImmutablePoint> locations) {
        this.locations = locations;
        unModified = Collections.unmodifiableMap(locations);
    }

    public Map<String, ImmutablePoint> getLocations(){
        return unModified;
    }

    public ImmutablePoint getLocation(String id){
        return locations.get(id);
    }

    public void setLocation(String id, long x, long y){
        if (locations.replace(id, new ImmutablePoint(x, y)) == null){
            throw new IllegalArgumentException("no such id exists");
        }
    }

}
