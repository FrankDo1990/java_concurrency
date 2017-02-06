package com.frank.concurrency.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Frank on 2017/1/30.
 */
public class FutureRender {

    private final ExecutorService executors = Executors.newCachedThreadPool();
    private static FutureRender instance ;

    private FutureRender(){

    }

    public static FutureRender getInstance(){
        if (instance == null){
            synchronized (FutureRender.class){
                if (instance == null){
                    instance = new FutureRender();
                }
            }
        }
        return instance;
    }

    public void Render(CharSequence source){
        if (source == null) return;
        List<ImageData> imageDataList = getImageDataFromSource(source);
        List<Future<ImageData>> byteFutures = new ArrayList<Future<ImageData>>();
        for (final ImageData data : imageDataList){
            Future<ImageData> f = executors.submit(new Callable<ImageData>() {
                public ImageData call() throws Exception {
                    return data.downLoad();
                }
            });
            byteFutures.add(f);
        }
        renderText(source);
    }

    private void renderImage(List<Future<ImageData>> futureList){

    }
    final class ImageData{
        private byte[] content;
        private int index;

        //simulate the downloadAction
        public ImageData downLoad(){
            return this;
        }
    }
    //simulate
    private List<ImageData> getImageDataFromSource(CharSequence source){
        return null;
    }
    //render text
    private void renderText(CharSequence source){
        return;
    }
}
