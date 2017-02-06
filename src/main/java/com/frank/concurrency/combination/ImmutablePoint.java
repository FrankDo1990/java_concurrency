package com.frank.concurrency.combination;

import com.frank.concurrency.annotations.Immutable;

/**
 * Created by dufeng on 2017/1/20.
 */
@Immutable
public class ImmutablePoint {
    final private long x, y;

    public ImmutablePoint() {
        this.x = 0;
        this.y = 0;
    }

    public ImmutablePoint(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }
}
