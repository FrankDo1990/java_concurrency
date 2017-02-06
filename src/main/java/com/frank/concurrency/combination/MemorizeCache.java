package com.frank.concurrency.combination;

import java.util.concurrent.*;

/**
 * Created by Frank on 2017/1/30.
 *
 */
public class MemorizeCache<A, V> {

    final private Computable<A, V> computable;
    final private ConcurrentHashMap<A, Future<V>> cache;

    public MemorizeCache(Computable<A, V> computable) {
        this.computable = computable;
        cache = new ConcurrentHashMap<>();
    }

    public V compute(final A arg) throws InterruptedException {
        while (true){
            Future<V> f = cache.get(arg);
            if (f == null){
                Callable<V> eval = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return computable.compute(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<V>(eval);
                f = cache.putIfAbsent(arg, ft);
                if (f != null){
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            }catch (CancellationException e) {
                cache.remove(arg);
            }catch (ExecutionException e) {
                cache.remove(arg);
                e.printStackTrace();
            }
        }
    }

    public static void main(String...args){
        MemorizeCache memorizeCache = new MemorizeCache<String, Long>(new ComputableImpl());
        //add code
    }

}
