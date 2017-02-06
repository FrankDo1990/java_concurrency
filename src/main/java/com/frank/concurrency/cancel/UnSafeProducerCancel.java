package com.frank.concurrency.cancel;

import java.math.BigInteger;
import java.util.concurrent.BlockingDeque;

/**
 * Created by Frank on 2017/2/7.
 */
public class UnSafeProducerCancel implements Runnable {

    private volatile boolean cancel = false;
    private final BlockingDeque<BigInteger> primes;

    public UnSafeProducerCancel(BlockingDeque<BigInteger> primes) {
        this.primes = primes;
    }

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!cancel){
            try {
                primes.put(p.nextProbablePrime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void consumer(){

    }
}
