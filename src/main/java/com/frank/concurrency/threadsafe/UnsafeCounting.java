package com.frank.concurrency.threadsafe;

import com.frank.concurrency.annotations.NotThreadSafe;

/**
 * Created by dufeng on 2017/1/19.
 */
@NotThreadSafe
public class UnsafeCounting {
    private long count = 0L;

    public long getCount() {
        return count;
    }

    public long addCount(){
        count += 1;
        return count;
    }

}


