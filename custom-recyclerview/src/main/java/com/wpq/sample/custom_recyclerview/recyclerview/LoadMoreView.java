package com.wpq.sample.custom_recyclerview.recyclerview;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wpq.sample.custom_recyclerview.R;

/**
 * 加载更多
 * @author wpq
 * @version 1.0
 */
public class LoadMoreView extends LinearLayout implements View.OnClickListener {

    /** 加载中... */
    public final static int STATE_LOADING = 0;
    /** 加载完成 */
    public final static int STATE_COMPLETE = 1;
    /** 加载失败，点击重试 */
    public final static int STATE_ERROR = 2;
    /** 没有更多了 */
    public final static int STATE_NOMORE = 3;

    private ProgressBar mPbLoadMore;
    private TextView mTvLoadMore;

    private String loadingHint;
    private String completeHint;
    private String errorHint;
    private String noMoreHint;

    private OnClickListener mOnClickListener;

    public LoadMoreView(Context context, OnClickListener onClickListener) {
        super(context);

        this.mOnClickListener = onClickListener;
        init();
    }

    private void init() {
        loadingHint = getResources().getString(R.string.LoadMoreView_loadingHint);
        completeHint = getResources().getString(R.string.LoadMoreView_completeHint);
        errorHint = getResources().getString(R.string.LoadMoreView_errorHint);
        noMoreHint = getResources().getString(R.string.LoadMoreView_noMoreHint);

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mPbLoadMore = new ProgressBar(getContext());
        LayoutParams lpPb = new LayoutParams(dp2px(20), dp2px(20));
        mPbLoadMore.setLayoutParams(lpPb);
        addView(mPbLoadMore);

        mTvLoadMore = new TextView(getContext());
        LayoutParams lpTv = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lpTv.setMargins(dp2px(10), dp2px(15), dp2px(10), dp2px(15));
        mTvLoadMore.setLayoutParams(lpTv);
        mTvLoadMore.setTextSize(14.0f);
        mTvLoadMore.setTextColor(0xFF888888);
        mTvLoadMore.setText(loadingHint);
        addView(mTvLoadMore);

        setOnClickListener(this);
        setClickable(false);

    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null) {
            mOnClickListener.onLoadMoreViewClick();
        }
    }

    /**
     * 定义 {@link #STATE_LOADING} 提示文字
     * @param loadingHint e.g. 加载中...
     * @return this LoadMoreView
     */
    public LoadMoreView setLoadingHint(String loadingHint) {
        this.loadingHint = loadingHint;
        return this;
    }

    /**
     * 定义 {@link #STATE_COMPLETE} 提示文字
     * @param completeHint e.g. 加载完成
     * @return this LoadMoreView
     */
    public LoadMoreView setCompleteHint(String completeHint) {
        this.completeHint = completeHint;
        return this;
    }

    /**
     * 定义 {@link #STATE_ERROR} 提示文字
     * @param errorHint e.g. 加载失败，点击重试
     * @return this LoadMoreView
     */
    public LoadMoreView setErrorHint(String errorHint) {
        this.errorHint = errorHint;
        return this;
    }

    /**
     * 定义 {@link #STATE_NOMORE} 提示文字
     * @param noMoreHint e.g. 没有更多了
     * @return
     */
    public LoadMoreView setNoMoreHint(String noMoreHint) {
        this.noMoreHint = noMoreHint;
        return this;
    }

    /**
     * 切换状态
     * @param state Pass {@link #STATE_LOADING} or {@link #STATE_COMPLETE} or {@link #STATE_NOMORE}
     */
    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                mTvLoadMore.setText(loadingHint);
                mPbLoadMore.setVisibility(View.VISIBLE);
                this.setClickable(false);
                break;
            case STATE_COMPLETE:
                mTvLoadMore.setText(completeHint);
                mPbLoadMore.setVisibility(View.GONE);
                this.setClickable(true);
                break;
            case STATE_ERROR:
                mTvLoadMore.setText(errorHint);
                mPbLoadMore.setVisibility(View.GONE);
                this.setClickable(true);
                break;
            case STATE_NOMORE:
                mTvLoadMore.setText(noMoreHint);
                mPbLoadMore.setVisibility(View.GONE);
                this.setClickable(false);
                break;
        }
    }

    private int dp2px(float dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    public interface OnClickListener{
        /** 加载更多 | 加载失败，点击重新加载 */
        void onLoadMoreViewClick();
    }
}
