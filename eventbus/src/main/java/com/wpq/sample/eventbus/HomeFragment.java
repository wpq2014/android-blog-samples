package com.wpq.sample.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wpq.sample.eventbus.annotation.BindEventBus;
import com.wpq.sample.eventbus.base.BaseFragment;
import com.wpq.sample.eventbus.eventbus.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wpq
 * @version 1.0
 */
@BindEventBus
public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_msg)
    TextView mTvMsg;
    @BindView(R.id.btn_intent)
    Button mBtnIntent;
    Unbinder unbinder;

    public static HomeFragment newInstance() {
        Bundle bundle = new Bundle();
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_intent)
    public void onViewClicked() {
        startActivity(TestActivity.newIntent(getActivity()));
    }

    /** 方法名可自定义 */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MessageEvent event) {
        if (event != null) {
            Log.e(TAG, "收到消息：【" + event.getMessage() +"】");
            mTvMsg.setText("收到消息：【" + event.getMessage() +"】");
        }
    }
}
