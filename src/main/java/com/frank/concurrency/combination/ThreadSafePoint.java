package com.frank.concurrency.combination;

import com.frank.concurrency.annotations.ThreadSafe;

/**
 * Created by dufeng on 2017/1/20.
 *
 */
@ThreadSafe
public class ThreadSafePoint {

    private long x;
    private long y;

    //防止竞态，不能实现this(p.x, p.y)
    public ThreadSafePoint(ThreadSafePoint p) {
        this(p.get());
    }

    public ThreadSafePoint(long []p){
        this(p[0], p[1]);
    }

    public ThreadSafePoint(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public synchronized void set(long x, long y){
        this.x = x;
        this.y = y;
    }

    public synchronized long[] get(){
        return new long[] {x, y};
    }


}
