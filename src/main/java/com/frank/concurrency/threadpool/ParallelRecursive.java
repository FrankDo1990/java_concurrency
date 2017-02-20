package com.frank.concurrency.threadpool;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by dufeng on 2017/2/20.
 */
public class ParallelRecursive {
    static class Node<T>{
        List<Node<T>> children;

        public Node(List<Node<T>> children) {
            this.children = children;
        }

        public T computes(){
            //未实现
            return null;
        }
    }

    static <T> Collection<T> sequentialRecursive(List<Node<T>> nodes, Collection<T> results){
        for (Node<T> n : nodes){
            T t = n.computes();
            if (t != null) {
                results.add(n.computes());
                sequentialRecursive(n.children, results);
            }
        }
        return null;
    }
    //并行提交任务——不保证results顺序一致
    static <T> void parallelRecursive(final ExecutorService exec, List<Node<T>> nodes, Collection<T> results){
        for (Node<T> n : nodes){
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    results.add(n.computes());
                }
            });
            parallelRecursive(exec, n.children, results);
        }
    }
    //获取提交结果
    static <T> Collection<T> getParallelRecursiveResults(List<Node<T>> nodes) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Queue<T> results = new ConcurrentLinkedQueue<T>();
        parallelRecursive(exec, nodes, results);
        exec.shutdown();
        exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        return results;
    }
}
