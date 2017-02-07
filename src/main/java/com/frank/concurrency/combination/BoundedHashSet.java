package com.frank.concurrency.combination;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Created by Frank on 2017/1/28.
 * 使用信号量设置有界Set
 */
public class BoundedHashSet<E> {
    private final Set<E> set;
    private final Semaphore semaphore;

    public BoundedHashSet(int bound) {
        semaphore = new Semaphore(bound);
        set = Collections.synchronizedSet(new HashSet<E>());
    }


    public boolean add(E e) throws InterruptedException{
        semaphore.acquire();
        boolean added = false;
        try{
            added = set.add(e);
            return added;
        }finally {
            if (!added){
                semaphore.release();
            }
        }
    }

    public boolean remove(E e){
        boolean remove = set.remove(e);
        if (remove){
            semaphore.release();
        }
        return remove;
    }
}
