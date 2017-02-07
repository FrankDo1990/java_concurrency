package com.frank.concurrency.threadsafe.escape.unsafe;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Frank on 2017/1/17.
 */
public class TestRun {

    UnSafePublish unSafePublish = new UnSafePublish();
    Holder holder = unSafePublish.holder;
    public static void main(String...args){
        TestRun testRun = new TestRun();
        testRun.runTest();
    }

    public void runTest(){
        ExecutorService executorService = Executors.newFixedThreadPool(100000);
        for (int i = 0; i < 100000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        holder.assertSanity();
                    }
                }
            });
        }
        long n = 50000000000000L;
        while (n-- > 0){
            holder.setN(new Random().nextInt());
        }
    }

}
