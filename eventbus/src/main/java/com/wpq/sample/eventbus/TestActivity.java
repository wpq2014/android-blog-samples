package com.wpq.sample.eventbus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.wpq.sample.eventbus.base.BaseAppCompatActivity;
import com.wpq.sample.eventbus.eventbus.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wpq
 * @version 1.0
 */
public class TestActivity extends BaseAppCompatActivity {

    @BindView(R.id.btn_post)
    Button mBtnPost;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, TestActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_post)
    public void onViewClicked() {
        EventBus.getDefault().post(new MessageEvent("嘿嘿嘿~"));
        finish();
    }

}
