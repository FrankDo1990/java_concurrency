package com.frank.concurrency.cancel;

import com.frank.concurrency.annotations.GuardedBy;
import com.google.common.collect.Lists;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Frank on 2017/2/6.
 */
public class PrimeGenerator implements Runnable {

    @GuardedBy("this")
    final List<BigInteger> primes = Lists.newArrayList();
    private volatile boolean cancel = false;

    public void cancel(){
        cancel = true;
    }

    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!cancel){
            p = p.nextProbablePrime();
            synchronized (this){
                primes.add(p);
            }
        }
    }

    public synchronized List<BigInteger> get(){
        return Lists.newArrayList(primes);
    }
}
