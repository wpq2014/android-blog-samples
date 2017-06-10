package com.wpq.sample.progress_dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.wpq.sample.progress_dialog.widget.CustomProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wpq
 * @version 1.0
 */
public class CustomProgressDialogActivity extends AppCompatActivity {

    @BindView(R.id.btn_show)
    Button mBtnShow;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CustomProgressDialogActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress_dialog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_show)
    public void onViewClicked() {
        CustomProgressDialog.showLoading(this, "加载中...", false);
        // 4s后dismiss
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CustomProgressDialog.stopLoading();
            }
        }, 4000);

        // 在1.5s的时候再次调用show
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CustomProgressDialog.showLoading(CustomProgressDialogActivity.this, "loading...");
            }
        }, 1500);
        // 3s时调一下
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CustomProgressDialog.stopLoading();
            }
        }, 3000);
    }
}
