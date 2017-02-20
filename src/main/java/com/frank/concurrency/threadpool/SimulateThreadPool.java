package com.frank.concurrency.threadpool;


import com.frank.concurrency.utils.Utils;

import java.util.concurrent.*;
import java.lang.Runtime;

/**
 * Created by dufeng on 2017/2/17.
 *
 */
public class SimulateThreadPool {
    /**
     * 单线程的Executor中执行线程依赖的任务———死锁 Thread Starvation deadlock
     */
    public static void simulateDeadLock(){
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Future<Boolean> res = exec.submit(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return true;
                    }
                }) ;
                return res.get();
            }
        });
        System.out.println("");
    }

    public static void simulateThreadPoolExecutor(){
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1);
        ExecutorService exec = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, queue);
        ((ThreadPoolExecutor)exec).setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 10 ; i++) {
            exec.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    Thread.sleep(1000L);
                    System.out.println("thread currentThread runs " + Thread.currentThread().getName());
                    return null;
                }
            });
        }
        exec.shutdown();
    }

    static void queueFullPolicy(RejectedExecutionHandler rejectedExecutionHandler){
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
         new ArrayBlockingQueue<Runnable>(2));
        // 设置线程池的拒绝策略为"丢弃"
        pool.setRejectedExecutionHandler(rejectedExecutionHandler);
        // 新建10个任务，并将它们添加到线程池中。
        class MyThread implements Runnable{
            private int i;

            public MyThread(int i) {
                this.i = i;
            }
            @Override
            public void run() {
                System.out.println(String.format("task %d runs in Thread %s", i, Thread.currentThread().getId()));
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            pool.execute(new MyThread(i));
        }
        pool.shutdown();
    }

    static void testCachedThreadPool(){
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i< 10000; i++){
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    Utils.formatPrintLn("thread id = {} runs", Thread.currentThread().getId());
                }
            });
        }
        exec.shutdown();
    }

    public static void main(String...args){
        //模拟单线程死锁
        //simulateDeadLock();
//        queueFullPolicy(new ThreadPoolExecutor.CallerRunsPolicy());
        testCachedThreadPool();
    }
}
