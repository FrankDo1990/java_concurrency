package com.frank.concurrency.cancel;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * Created by Frank on 2017/2/8.
 */
public abstract class SocketUsingTask<T> implements CancellableTask {

    private Socket socket;

    public synchronized void cancel(){
        try{
            if (socket != null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SocketUsingTask(Socket socket) {
        this.socket = socket;
    }

    public SocketUsingTask setSocket(Socket socket) {
        this.socket = socket;
        return this;
    }

    public RunnableFuture<T> newTask() {
        return new FutureTask<T>(this){
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                try{
                    SocketUsingTask.this.cancel();
                }finally {
                    return super.cancel(mayInterruptIfRunning);
                }
            }
        };
    }

}