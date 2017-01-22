package com.frank.concurrency.combination;

/**
 * Created by dufeng on 2017/1/20.
 */
public class MutablePoint {
    private long x;
    private long y;

    public MutablePoint() {
        this.x = 0;
        this.y = 0;
    }

    public MutablePoint(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }
}
