package com.frank.concurrency.executors;

import com.frank.concurrency.utils.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Frank on 2017/1/30.
 */
public class ExecutorsLifeCycle {

    public static void main(String...args){
        ExecutorsLifeCycle executorsLifeCycle = new ExecutorsLifeCycle();
        executorsLifeCycle.runExecutorLifeCycles();
    }

    public void runExecutorLifeCycles(){
        ExecutorService service = Executors.newFixedThreadPool(5);
        service.shutdown();
        Utils.formatPrintLn("{} isTerminated = {}", service, service.isTerminated());
        Utils.formatPrintLn("{} isShutDown = {}", service, service.isShutdown());

    }

    public void addTask(ExecutorService executorService){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
