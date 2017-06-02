package com.wpq.sample.loading;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.wpq.sample.loading.loading.LoadingController;
import com.wpq.sample.loading.loading.LoadingInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WuPuquan on 2017/6/2.
 */

public class MultiLoadingActivity extends AppCompatActivity {

    public static final int NETWORK_ERROR = 0;
    public static final int ERROR = 1;
    public static final int EMPTY = 2;
    public static final int CONTENT = 3;

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private LoadingController videoLoadingController;
    private LoadingController listLoadingController;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MultiLoadingActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_loading);
        ButterKnife.bind(this);

        requestVideo();
        requestList();
    }

    private void requestVideo() {
        videoLoadingController = new LoadingController.Builder(this, imageView)
                .setOnErrorRetryClickListener(new LoadingInterface.OnClickListener() {
                    @Override
                    public void onClick() {
                        refreshVideo(CONTENT);
                    }
                })
                .build();
        refreshVideo(NETWORK_ERROR);
    }

    private void refreshVideo(final int target) {
        videoLoadingController.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (target) {
                    case NETWORK_ERROR:
                        videoLoadingController.showError();
                        break;
                    case CONTENT:
                        videoLoadingController.dismissLoading();
//                        videoView.
                        break;
                }
            }
        }, 3000);
    }

    private void requestList() {
        listLoadingController = new LoadingController.Builder(this, recyclerView)
                .setErrorImageResoruce(R.drawable.error)
                .setOnNetworkErrorRetryClickListener(new LoadingInterface.OnClickListener() {
                    @Override
                    public void onClick() {
                        refreshList(EMPTY);
                    }
                })
                .setEmptyMessage("暂无数据~")
                .setOnEmptyTodoClickListener("刷新", new LoadingInterface.OnClickListener() {
                    @Override
                    public void onClick() {
                        refreshList(EMPTY);
                    }
                })
                .build();
        refreshList(ERROR);
    }

    private void refreshList(final int target) {
        listLoadingController.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (target) {
                    case ERROR:
                        listLoadingController.showNetworkError();
                        break;
                    case EMPTY:
                        listLoadingController.showEmpty();
                        break;
                }
            }
        }, 3000);
    }
}
