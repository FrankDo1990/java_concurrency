package com.frank.concurrency.cancel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by dufeng on 2017/2/7.
 * 在外部线程（new Runnable...）中断, timedRun可以被任意线程调用，但是由于无法知道调用timedRun()线程的中断策略，这种方法不安全
 * 理想策略是中断线程之前了解线程中断策略
 */
public class OutterThreadInterrupted {

    private static final ScheduledExecutorService exec;

    static {
        exec = Executors.newScheduledThreadPool(100);
    }

    public void timedRun(Runnable r, long timeout, TimeUnit unit){
        Thread curThread = Thread.currentThread();
        exec.schedule(new Runnable() {
            @Override
            public void run() {
                curThread.interrupt();
            }
        }, timeout, unit);
        r.run();
    }
}
