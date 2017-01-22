package com.frank.concurrency.combination;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by dufeng on 2017/1/20.
 */
public class NumberRange {
    private final AtomicLong up = new AtomicLong();
    private final AtomicLong low = new AtomicLong();

    public NumberRange() {
    }

    public void setUp(long x){
        if (x <= low.get()){
            throw new IllegalStateException("up < low");
        }
        up.set(x);
    }

    public void setLow(long x){
        if (x >= up.get()){
            throw new IllegalStateException("up < low");
        }
        low.set(x);
    }
}
