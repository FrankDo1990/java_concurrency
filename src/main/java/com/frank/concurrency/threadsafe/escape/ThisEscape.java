package com.frank.concurrency.threadsafe.escape;

import javafx.event.Event;

/**
 * Created by Frank on 2017/1/15.
 *  在构造方法中溢出
 */
public class ThisEscape {
    public ThisEscape(EventSource source) {
        source.registerListener(new EventListener() {
            @Override
            public void onEvent(Event event) {
                //do somethings, will escape this
                ThisEscape escape = ThisEscape.this;
            }
        });
    }
}
