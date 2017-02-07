package com.frank.concurrency.utils;

/**
 * Created by Frank on 2017/1/28.
 */
public class Utils {

    public static void formatPrintLn(String format, Object...args){
        if (format == null || format.length() == 0) {
            System.out.println("");
            return;
        }
        String toSub = "\\{\\}";
        String result = String.format(format.replaceAll(toSub, "%s"), args);
        System.out.println(result);
    }


}
