package com.wpq.sample.custom_recyclerview.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.wpq.sample.custom_recyclerview.bean.Girl;
import com.wpq.sample.custom_recyclerview.util.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wpq
 * @version 1.0
 */
public class GirlService extends IntentService {

    public static final String INTENT_KEY_GIRLS = "girls";

    public GirlService() {
        super("");
    }

    public static void startService(Context context, List<Girl> list) {
        Intent intent = new Intent(context, GirlService.class);
        intent.putParcelableArrayListExtra(INTENT_KEY_GIRLS, (ArrayList<? extends Parcelable>) list);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }

        List<Girl> list = intent.getParcelableArrayListExtra(INTENT_KEY_GIRLS);
        handleGirls(list);
    }

    private void handleGirls(List<Girl> list) {
        if (list == null || list.size() == 0) {
            EventBus.getDefault().post(new ArrayList<Girl>());
            return;
        }
        for (Girl girl : list) {
            Bitmap bitmap = ImageLoader.load(this, girl.getUrl());
            if (bitmap != null) {
                girl.setWidth(bitmap.getWidth());
                girl.setHeight(bitmap.getHeight());
            }
        }
        EventBus.getDefault().post(list);
    }

}
