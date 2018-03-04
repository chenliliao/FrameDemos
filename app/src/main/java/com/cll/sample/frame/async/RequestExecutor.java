package com.cll.sample.frame.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cll on 2018/3/4.
 */

public enum RequestExecutor {
    SINGLE;


    private ExecutorService sExecutorService;
    RequestExecutor(){
        sExecutorService = Executors.newSingleThreadExecutor();
    }

    public void execute(final Callback callback){
        sExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                callback.execute();
            }
        });
    }

    public interface Callback{
        void execute();
    }
}
