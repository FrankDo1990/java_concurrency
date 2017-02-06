package com.frank.concurrency.threadsafe.escape.unsafe;

/**
 * Created by Frank on 2017/1/17.
 */
public class UnSafePublish {

    public Holder holder;

    public UnSafePublish() {
        holder = new Holder(42);
    }

    public void initialize(){
        holder = new Holder(43);
    }

}
