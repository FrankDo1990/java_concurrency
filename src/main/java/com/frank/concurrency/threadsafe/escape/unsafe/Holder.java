package com.frank.concurrency.threadsafe.escape.unsafe;

/**
 * Created by Frank on 2017/1/16.
 */
public class Holder {
    private int n;

    public Holder(int n) {
        this.n = n;
    }

    public void assertSanity(){
        System.out.println("thread " + Thread.currentThread().getName() + "n =" + n + "hold = " + this);
        if (n != n){
            throw new AssertionError("the statement is false");
        }
    }

    public Holder setN(int n) {
        this.n = n;
        return this;
    }
}
