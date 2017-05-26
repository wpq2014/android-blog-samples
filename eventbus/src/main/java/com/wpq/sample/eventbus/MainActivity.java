package com.wpq.sample.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.wpq.sample.eventbus.annotation.BindEventBus;
import com.wpq.sample.eventbus.base.BaseAppCompatActivity;
import com.wpq.sample.eventbus.eventbus.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@BindEventBus
public class MainActivity extends BaseAppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.tv_msg)
    TextView mTvMsg;
    @BindView(R.id.btn_intent)
    Button mBtnIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MessageEvent event) {
        if (event != null) {
            Log.e(TAG, event.getMessage());
            mTvMsg.setText("收到消息：【" + event.getMessage() +"】");
        }
    }

    @OnClick(R.id.btn_intent)
    public void onViewClicked() {
        startActivity(new Intent(this, TestActivity.class));
    }

}
