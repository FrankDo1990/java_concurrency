package com.frank.concurrency.problems;

import java.util.concurrent.*;

/**
 * Created by dufeng on 2016/12/23.
 * 实现一个流控程序。控制客户端每秒调用某个远程服务不超过N次，客户端是会多线程并发调用，需要一个轻量简洁的实现
 */
public class ConcurrencyProblems001 {
    public static final int MAX_QPS = 10;
    public static final int MAX_EXECUTE_TIME = 100;
    public static  int preRpc = MAX_EXECUTE_TIME;
    public static final CountDownLatch latch = new CountDownLatch(MAX_EXECUTE_TIME);

    public static void main(String...args){
        //监视器
        monitor();
        solution1();
    }

    public static void monitor(){
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                int lastRpc = (int)(preRpc - latch.getCount());
                System.out.println("last 1 seconds, execute " + lastRpc + " times");
                preRpc = (int) latch.getCount();
            }
        }, 0L, 1000L, TimeUnit.MILLISECONDS);
    }
    public static void solution1(){
        //信号量同步
        Semaphore semaphore = new Semaphore(MAX_QPS);
        //每50s释放一半信号量
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                int releaseNum = MAX_QPS - semaphore.availablePermits();
                System.out.println("realease num = " + releaseNum);
                if (releaseNum > 0) {
                    semaphore.release(releaseNum);
                }
            }
        }, 1000L, 1000L, TimeUnit.MILLISECONDS);
        int j = 10000;
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        while (j > 0){
            int i = 1000;
            while (i > 0){
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        semaphore.acquireUninterruptibly(1);
                        simulateRpc();
                    }
                });
                i--;
            }
            j--;
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdownNow();
            System.out.print("done");
        }
    }


    public static void simulateRpc(){
        System.out.println(String.format("%s execute rpc, total exe = %d", Thread.currentThread().getName(), MAX_EXECUTE_TIME - latch.getCount()));
        latch.countDown();
    }
}
