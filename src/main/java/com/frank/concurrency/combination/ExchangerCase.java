package com.frank.concurrency.combination;

import com.frank.concurrency.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;

/**
 * Created by Frank on 2017/1/29.
 */
public class ExchangerCase {
    private final Exchanger<List<Integer>> exchanger;
    private final Random random;

    public ExchangerCase() {
        exchanger = new Exchanger<>();
        random = new Random();
    }

    public void start(){
        new Thread(new Consumer()).start();
        new Thread(new Producer()).start();
    }

    public static void main(String...args){
        ExchangerCase exchangerCase = new ExchangerCase();
        exchangerCase.start();
    }

    class Consumer implements Runnable{
        List<Integer> list = new ArrayList<>();
        @Override
        public void run() {
            for (int i = 0; i < 10; i++){
                list.clear();
                try {
                    Thread.sleep(1000 + random.nextInt(10000));
                    list = exchanger.exchange(list);
                    Utils.formatPrintLn("consumer list after exchange = {}", list);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Producer implements Runnable{
        List<Integer> list = new ArrayList<>();
        @Override
        public void run() {
            for (int i = 0; i < 10; i++){
                list.add(random.nextInt(100));
                list.add(random.nextInt(100));
                list.add(random.nextInt(100));
                list.add(random.nextInt(100));
                list.add(random.nextInt(100));
                try {
                    Thread.sleep(1000 + random.nextInt(10000));

                    list = exchanger.exchange(list);
                    Utils.formatPrintLn("producer list after exchange = {}", list);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
