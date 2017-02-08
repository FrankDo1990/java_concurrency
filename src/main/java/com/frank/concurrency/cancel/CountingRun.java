package com.frank.concurrency.cancel;

import java.util.concurrent.*;

/**
 * Created by Frank on 2017/2/7.
 * 终端现成的
 */
public class CountingRun {

    private final ScheduledExecutorService exec;

    public CountingRun(ScheduledExecutorService exec) {
        this.exec = exec;
    }

    /**
     * unsafe 在外部线程中断任务线程,由于unSafeTimedRun可以被不同的线程调用，因为中断线程并不知道taskThread的中断策略
     * @param r
     * @param timeout
     * @param unit
     */
    public void unSafeTimedRun(Runnable r, long timeout, TimeUnit unit){
        Thread taskThread = Thread.currentThread();
        exec.schedule(new Runnable() {
            @Override
            public void run() {
                //发出中断标志
                taskThread.interrupt();
            }
        }, timeout, unit);
        //任务线程执行
        r.run();
    }

    /**
     * 使用专门的线程执行任务的中断
     * @param r
     * @param timeout
     * @param unit
     * @throws Throwable
     * @throws InterruptedException
     */
    public void safeJoinTimedRun(Runnable r, long timeout, TimeUnit unit) throws Throwable, InterruptedException {
        class ReThrowableThread implements Runnable{
            private volatile Throwable t;
            @Override
            public void run() {
                try {
                    r.run();
                } catch (Throwable t){
                    this.t = t;
                }
            }
            private void reThrow() throws Throwable{
                if (t != null){
                    throw new Throwable(t);
                }
            }
        }
        ReThrowableThread task = new ReThrowableThread();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        exec.schedule(new Runnable() {
            @Override
            public void run() {
                taskThread.interrupt();
            }
        }, timeout, unit);
        //使用join方法确保taskThread的执行和返回时间——不足之处是不知道taskThread是由于timeout还是正常出现
        taskThread.join(unit.toMillis(timeout));
        //继续抛出中断给上层调用栈
        task.reThrow();
    }

    public void futureCancelTimedRun(Runnable r, long timeout, TimeUnit unit) throws ExecutionException, InterruptedException {
        Future<?> task = exec.submit(r);
        try {
            task.get(timeout, unit);
        } catch (InterruptedException e) {
            throw e;
        } catch (ExecutionException e) {
            throw e;
        } catch (TimeoutException e) {
            //接下来任务取消
        }finally {
            task.cancel(true);
        }
    }
}
