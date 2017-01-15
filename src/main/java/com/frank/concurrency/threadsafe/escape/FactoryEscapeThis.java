package com.frank.concurrency.threadsafe.escape;

import javafx.event.Event;

/**
 * Created by Frank on 2017/1/15.
 * 使用工厂模式构造，防止构造方法中this escape
 */
public class FactoryEscapeThis {
    private final EventListener listener;

    private FactoryEscapeThis(){
        listener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                //do somethings
            }
        };
    }

    public FactoryEscapeThis getInstance(EventSource source){
        FactoryEscapeThis factoryEscapeThis = new FactoryEscapeThis();
        source.registerListener(listener);
        return factoryEscapeThis;
    }
}
