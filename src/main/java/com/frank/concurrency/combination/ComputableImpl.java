package com.frank.concurrency.combination;

/**
 * Created by Frank on 2017/1/30.
 */
public class ComputableImpl implements Computable<String, Long> {
    @Override
    public Long compute(String arg) throws InterruptedException {
        return arg == null || arg.isEmpty() ? -1L : Long.parseLong(arg);
    }
}
