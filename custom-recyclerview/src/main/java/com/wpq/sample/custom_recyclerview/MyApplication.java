package com.wpq.sample.custom_recyclerview;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author wpq
 * @version 1.0
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
    }
}
