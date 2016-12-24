package com.wenwen.concurrency.problems;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Frank on 2016/12/24.
 */
public class Problems001 {

    public static final int MAX_QPS = 10;
    public static final int MAX_EXECUTE = 100;
    static int pre = MAX_EXECUTE;
    public static CountDownLatch latch = new CountDownLatch(MAX_EXECUTE);
    public static AtomicInteger atomicInteger = new AtomicInteger(MAX_QPS);

    public static void main(String...args){
//        monitor();
        solution2();
    }
    //监视线程
    public static void monitor(){
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        System.out.println(String.format("last sec executes %d", pre - (int) latch.getCount()));
                        pre = (int) latch.getCount();
                    }
                }, 1000L, 1000L, TimeUnit.MILLISECONDS
        );
    }
    //Solution, 使用AtomicInteger
    public static void solution2(){
        //定时重置信号量为MAX_QPS
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            public void run() {
                atomicInteger.getAndSet(MAX_QPS);
            }
        }, 1000L, 1000L, TimeUnit.MILLISECONDS);
        ExecutorService pool = Executors.newFixedThreadPool(10);
        int j = 1000000;
        for (;j >0 ; j--){
            int x = 100;
            for (; x > 0; x --){
                pool.submit(new Runnable() {
                    public void run() {
                        if (atomicInteger.decrementAndGet() >= 0){
                            simulateRpc();
                        }
                    }
                });
            }
        }
        pool.shutdownNow();
        try {
            latch.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pool.shutdownNow();
        }
    }
    //模拟rpc
    public static void simulateRpc(){
        System.out.println(String.format("%s call rpc !", Thread.currentThread().getName()));
        latch.countDown();
    }

}
