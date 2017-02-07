package com.frank.concurrency.combination;

import com.frank.concurrency.utils.Utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Frank on 2017/1/28.
 */
public class TestLatch {

    public static void main(String...args){
        TestLatch testLatch = new TestLatch();
        testLatch.countDownLatch();
    }

    public void countDownLatch(){
        final int nThread = 10;
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(nThread);
        Executor executor = Executors.newFixedThreadPool(nThread);
        for (int i = 0; i < nThread; i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        startLatch.await();
                        doTask();
                        endLatch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        //置中断标志
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Utils.formatPrintLn("thread now runs");
        startLatch.countDown();
        try {
            endLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Utils.formatPrintLn("end of task");
    }

    private void doTask(){
        Utils.formatPrintLn("{} run task", Thread.currentThread().getName());
    }
}
