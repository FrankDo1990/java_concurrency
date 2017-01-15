package com.frank.concurrency.threadsafe;

import com.frank.concurrency.annotations.GuardedBy;
import com.frank.concurrency.annotations.ThreadSafe;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by dufeng on 2016/11/4.
 */
@ThreadSafe
public class CachedFactorizer extends MyServlet{
    @GuardedBy("this") private BigInteger lastNumber;
    @GuardedBy("this") private BigInteger[] lastFactors;
    @GuardedBy("this") private long hits;
    @GuardedBy("this") private long cacheHits;

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        super.service(servletRequest, servletResponse);
        BigInteger i = (BigInteger) extractFromRequest(servletRequest, servletResponse);
        BigInteger[] factors = null;
        synchronized (this){
            ++hits;
            if (lastNumber == i){
                ++cacheHits;
                factors = lastFactors.clone();
            }
        }
        if (factors == null){
            factors = factor(i);
            synchronized (this){
                lastNumber = i;
                lastFactors = factors;
            }
        }
        encodeFactor2Resp(servletResponse, factors);
    }
    private void encodeFactor2Resp(ServletResponse response, BigInteger[] factors){

    }
    //factor function
    private BigInteger[] factor(BigInteger i){
        return new BigInteger[1];
    }
    public synchronized long getHits(){
        return hits;
    }
    public synchronized double getHitRatio(){
        return (double) cacheHits/ (double) hits;
    }
    //get param form request
    private Object extractFromRequest(ServletRequest servletRequest, ServletResponse servletResponse){
        return servletRequest.getAttribute("param");
    }
}
