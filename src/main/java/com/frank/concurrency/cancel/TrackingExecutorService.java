package com.frank.concurrency.cancel;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by dufeng on 2017/2/17.
 * shutDownNow方法：
 * Attempts to stop all actively executing tasks, halts the
 * processing of waiting tasks, and returns a list of the tasks
 * that were awaiting execution.
 * 局限性在于不能获取到executorService调用shutDownNow时中断的任务——采用装饰者模式，封装ExecutorService,
 * 将TrackingExecutorService委托到ExecutorService中
 */
public class TrackingExecutorService extends AbstractExecutorService {

    private final ExecutorService exec;
    private final Set<Runnable> taskCancelledAtShutDown = Sets.newConcurrentHashSet();

    public TrackingExecutorService(ExecutorService exec) {
        this.exec = exec;
    }

    public List<Runnable> getCancelledTasks(){
        if (!exec.isTerminated()){
            throw new IllegalStateException("not terminate");
        }
        return Lists.newArrayList(taskCancelledAtShutDown);
    }

    @Override
    public void shutdown() {
        exec.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return exec.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return exec.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return exec.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return exec.awaitTermination(timeout, unit);
    }

    @Override
    public void execute(Runnable command) {
        exec.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    command.run();
                }finally {
                    if (isShutdown() && Thread.currentThread().isInterrupted()){
                        taskCancelledAtShutDown.add(command);
                    }
                }
            }
        });
    }
}
