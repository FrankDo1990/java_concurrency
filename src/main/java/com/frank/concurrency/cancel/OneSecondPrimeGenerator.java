package com.frank.concurrency.cancel;

import com.frank.concurrency.utils.Utils;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Frank on 2017/2/6.
 */
public class OneSecondPrimeGenerator  {

    public static void main(String...args){
        List<BigInteger> result = runGenerator();
        Utils.formatPrintLn("result = {}", result);
    }

    public static List<BigInteger> runGenerator() {
        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            generator.cancel();
        }
        return generator.get();
    }
}
