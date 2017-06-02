package com.wpq.sample.loading;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.wpq.sample.loading.loading.LoadingController;
import com.wpq.sample.loading.loading.LoadingInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WuPuquan on 2017/6/2.
 */

public class SingleLoadingActivity extends AppCompatActivity {

    public static final int NETWORK_ERROR = 0;
    public static final int ERROR = 1;
    public static final int EMPTY = 2;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private LoadingController loadingController;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SingleLoadingActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_loading);
        ButterKnife.bind(this);

        request();
    }

    private void request() {
        loadingController = new LoadingController.Builder(this, recyclerView)
                .setLoadingImageResource(R.drawable.loading_frame_anim)
                .setLoadingMessage("加载中...")
                .setErrorMessage("加载失败，请稍后重试~")
                .setErrorImageResoruce(R.drawable.error)
                .setEmptyMessage("暂无数据~")
                .setOnNetworkErrorRetryClickListener(new LoadingInterface.OnClickListener() {
                    @Override
                    public void onClick() {
                        refresh(ERROR);
                    }
                })
                .setOnErrorRetryClickListener("点我重试", new LoadingInterface.OnClickListener() {
                    @Override
                    public void onClick() {
                        refresh(EMPTY);
                    }
                })
                .build();

        refresh(NETWORK_ERROR);
    }

    private void refresh(final int showTarget) {
        loadingController.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (showTarget) {
                    case NETWORK_ERROR:
                        loadingController.showNetworkError();
                        break;
                    case ERROR:
                        loadingController.showError();
                        break;
                    case EMPTY:
                        loadingController.showEmpty();
                        break;
                }
            }
        }, 3000);
    }
}
