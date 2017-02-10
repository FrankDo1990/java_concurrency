package com.frank.concurrency.cancel;

import java.net.Socket;
import java.util.concurrent.*;

/**
 * Created by Frank on 2017/2/8.
 *
 */
public class CancellingExecutor extends ThreadPoolExecutor {
    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public static CancellingExecutor getCancellingExecutor(){
        return new CancellingExecutor(1, 5, 1000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100));
    }

    /**
     * 通过newTaskFor的方法将非标准的取消操作封装到任务当中
     * @param callable
     * @param <T>
     * @return
     */
    protected<T> RunnableFuture<T> newTaskFor(Callable<T> callable){
        if (callable instanceof CancellableTask){
            return ((CancellableTask) callable).newTask();
        }else {
            return super.newTaskFor(callable);
        }
    }

    public static void main(String...args){
        CancellingExecutor exec = CancellingExecutor.getCancellingExecutor();
        Socket socket = null;
        //socket初始化
        exec.newTaskFor(new SocketUsingTask(socket) {
            @Override
            public byte[] call() throws Exception {
                //线程执行方法
                return new byte[0];
            }
        });
    }
}
