package com.frank.concurrency.combination;

import com.frank.concurrency.annotations.NotThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dufeng on 2017/1/20.
 */
@NotThreadSafe
/**
 * list 和 listHelper没有在
 */
public class ListHelper<E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    public synchronized boolean putIfAbsent(E x){
        boolean absent = !list.contains(x);
        if (absent){
            list.add(x);
        }
        return absent;
    }
}
