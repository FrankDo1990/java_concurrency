package com.frank.concurrency.cancel;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * Created by dufeng on 2017/2/7.
 */
public class Task {
    /**
     * 不可取消的任务将中断状态传递到调用栈上层
     * @param queue
     * @return
     */
    public Task getNextTask(BlockingQueue<Task> queue){
        boolean interrupted = false;
        try{
            while (true){
                return queue.take();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            //清理代码处理
            return null;
        } finally {
            // 不可取消的任务退出之前重置中断标志
            if (interrupted){
                Thread.currentThread().interrupt();
            }
        }
    }
}
