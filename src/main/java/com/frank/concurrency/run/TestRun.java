package com.frank.concurrency.run;

import com.frank.concurrency.threadsafe.UnsafeCounting;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by dufeng on 2017/1/19.
 */
public class TestRun {
    private UnsafeCounting counting = new UnsafeCounting();
    private Set<Long> countSet = Sets.newConcurrentHashSet();

    public void testUnSafeCounting(){
        ExecutorService executors = Executors.newFixedThreadPool(2);
        Collection<Callable<Integer>> tasks = Lists.newArrayList();
        for (int i = 0; i < 2; i++){
            tasks.add(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    while (true) {
                        long res = counting.addCount();
                        if (countSet.contains(res)){
                            System.out.println("err :" + res);
                            break;
                        }
                        countSet.add(res);
                    }
                    return countSet.size();
                }
            });
        }
        try {
            executors.invokeAll(tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String...args){
        TestRun run = new TestRun();
//        run.testUnSafeCounting();
        run.testUnmodifiedMap();
    }


    public void testUnmodifiedMap(){
        Map<String, String> map = Maps.newHashMap();
        map.put("id", new String("id"));
        Map<String, String> unmodified = Collections.unmodifiableMap(map);
        System.out.println(unmodified.get("id").hashCode());
        System.out.println(map.get("id").hashCode());
    }

}
