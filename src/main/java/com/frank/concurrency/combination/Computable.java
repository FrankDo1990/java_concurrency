package com.frank.concurrency.combination;

/**
 * Created by Frank on 2017/1/30.
 */
public interface Computable<A, V> {
    public V compute(A arg) throws InterruptedException;
}
