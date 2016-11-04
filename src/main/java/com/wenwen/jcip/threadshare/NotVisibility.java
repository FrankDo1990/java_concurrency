package com.wenwen.jcip.threadshare;

/**
 * Created by dufeng on 2016/11/4.
 */
public class NotVisibility {
    private static boolean ready;
    private static int number;

    private static class ReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            while(!ready){
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String args[]){
        new ReadThread().start();
        number = 42;
        ready = true;
    }
}
