package com.frank.concurrency.cancel;

import java.math.BigInteger;
import java.util.concurrent.BlockingDeque;

/**
 * Created by Frank on 2017/2/7.
 */
public class UnsafeConsumerCancel implements Runnable {

    private final BlockingDeque<BigInteger> primes;

    public UnsafeConsumerCancel(BlockingDeque<BigInteger> primes) {
        this.primes = primes;
    }

    @Override
    public void run() {

    }
}
