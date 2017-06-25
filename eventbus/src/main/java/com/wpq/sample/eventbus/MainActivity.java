package com.wpq.sample.eventbus;

import android.os.Bundle;
import android.util.Log;

import com.wpq.sample.eventbus.annotation.BindEventBus;
import com.wpq.sample.eventbus.base.BaseAppCompatActivity;
import com.wpq.sample.eventbus.eventbus.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

@BindEventBus
public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction().add(R.id.container, HomeFragment.newInstance()).commit();
    }

    /** 方法名可自定义 */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MessageEvent event) {
        if (event != null) {
            Log.e(TAG, "收到消息：【" + event.getMessage() +"】");
        }
    }

}
