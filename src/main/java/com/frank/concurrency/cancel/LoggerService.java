package com.frank.concurrency.cancel;

import java.io.PrintWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by dufeng on 2017/2/9.
 * 简单的实现log服务...
 */
public class LoggerService {
    private static final int QUEUE_SIZE = 1000;
    private final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(QUEUE_SIZE);
    private boolean isShutDown = false;
    private int reverse = 0;
    private ReadThread readThread = null;
    /**
     * log 服务,写入log
      */
    public void log(String message) throws InterruptedException {
        synchronized (this){
            if (isShutDown){
                throw new IllegalStateException("log service has been shutdown");
            }
            reverse++;
        }
        queue.put(message);
    }

    /**
     * 日志服务启动
     */
    public void startService(){
        PrintWriter printWriter = null; //printWriter初始化
        this.readThread = new ReadThread(printWriter);
        readThread.start();
    }

    /**
     * 日志服务关闭
     */
    public synchronized void shutDown(){
        this.isShutDown = true;
        readThread.interrupt();
    }

    /**
     * 立刻关闭-非平滑处理
     */
    public synchronized void shutDownNow(){
        this.isShutDown = true;
        reverse = 0;
        readThread.interrupt();
    }

    /**
     * 日志打印线程
     */
    class ReadThread extends Thread{

        PrintWriter printWriter;

        private ReadThread(PrintWriter printWriter){
            this.printWriter = printWriter;
        }

        @Override
        public void run() {
            while (true){
                try{
                    synchronized (LoggerService.this){
                        if (isShutDown && reverse == 0)
                            break;
                    }
                    String message = queue.take();
                    printWriter.println(message);
                    synchronized (LoggerService.this){
                        reverse--;
                    }
                } catch (InterruptedException e) {
                    //retry
                    e.printStackTrace();
                } finally {
                    //关闭printWriter
                    printWriter.close();
                }
            }
        }
    }
}
