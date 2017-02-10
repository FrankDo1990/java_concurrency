package com.frank.concurrency.cancel;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/**
 * Created by Frank on 2017/2/8.
 */
public interface CancellableTask<T> extends Callable<T> {
    void cancel();
    RunnableFuture<T> newTask();
}
