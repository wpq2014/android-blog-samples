package com.wpq.sample.custom_recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wpq.sample.custom_recyclerview.adapter.StaggeredGridLayoutManagerAdapter;
import com.wpq.sample.custom_recyclerview.api.ApiHelper;
import com.wpq.sample.custom_recyclerview.api.RetrofitService;
import com.wpq.sample.custom_recyclerview.bean.Girl;
import com.wpq.sample.custom_recyclerview.recyclerview.BaseRecyclerAdapter;
import com.wpq.sample.custom_recyclerview.recyclerview.MyRecyclerView;
import com.wpq.sample.custom_recyclerview.service.GirlService;
import com.wpq.sample.custom_recyclerview.widget.HeaderAndFooterView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author wpq
 * @version 1.0
 */
public class StaggeredGridlayoutManagerActivity extends AppCompatActivity {

    public static final String TAG = StaggeredGridlayoutManagerActivity.class.getSimpleName();
    /** 每页20条，这个是当前Api定死的，不能自定义 */
    public static final int PAGE_COUNT = 20;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView)
    MyRecyclerView mRecyclerView;

    private StaggeredGridLayoutManagerAdapter mAdapter;
    private List<Girl> mList = new ArrayList<>();

    private int page = 2653; // 当前用的接口最多2655页数据

    private boolean isRefresh = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggeredgridlayoutmanager);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1; // 从1开始
                isRefresh = true;
                showTime();
            }
        });

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        // 解决滚动时item跳动、左右切换的问题
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(layoutManager);

//        mRecyclerView.addItemDecoration(new GridSpaceItemDecoration(2, ScreenUtils.dp2px(this, 3f), false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLoadMoreEnabled(true);
        mRecyclerView.setOnLoadListener(new MyRecyclerView.OnLoadListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                showTime();
            }
        });

        View header0 = new HeaderAndFooterView(this, 0xff235840, "header0");
        mRecyclerView.addHeaderView(header0);
        View header1 = new HeaderAndFooterView(this, 0xff840395, "header1");
        mRecyclerView.addHeaderView(header1);

        mAdapter = new StaggeredGridLayoutManagerAdapter(mList);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                try {
                    Girl girl = mList.get(position - mRecyclerView.getHeadersCount());
                    Toast.makeText(StaggeredGridlayoutManagerActivity.this, "单击第 " + position + " 项：" + girl.getTitle(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {}
            }
        });
        mAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                try {
                    Girl girl = mList.get(position - mRecyclerView.getHeadersCount());
                    Toast.makeText(StaggeredGridlayoutManagerActivity.this, "长按第 " + position + " 项：" + girl.getTitle(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {}
                return true;
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        showTime();
    }

    /** 一大波美女即将登场 */
    private void showTime() {
        // 使用豆瓣美女api 接口：http://www.dbmeinv.com/dbgroup/show.htm?cid=4&pager_offset=1
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.dbmeinv.com/dbgroup/")
                .client(new OkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);
        Call<String> call = service.getGirls("4", page);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, response.code() + ", " + response.isSuccessful() + ", " + response.message());
                if (response.isSuccessful()) {
//                    Log.e(TAG, response.body() + "");
                    List<Girl> girls = ApiHelper.parseGirls(response.body());
                    GirlService.startService(StaggeredGridlayoutManagerActivity.this, girls);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                mRecyclerView.loadMoreError();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_staggeredgridlayoutmanager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_more_than_one_page: // 一页以上
                page = 2653;
                isRefresh = true;
                showTime();
                break;
            case R.id.action_one_page: // 只有一页
                page = 2655;
                isRefresh = true;
                showTime();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(List<Girl> girls) {
        if (girls == null || girls.size() == 0) {
            return;
        }
        if (isRefresh) {
            mSwipeRefreshLayout.setRefreshing(false);
            mRecyclerView.scrollToPosition(0);
            mList.clear();
            mAdapter.notifyDataSetChanged();
        }
        mList.addAll(girls);
        mAdapter.notifyItemInserted(mRecyclerView.getHeadersCount() + mList.size());
        if (mList.size() < PAGE_COUNT) {
            mRecyclerView.noNeedToLoadMore();
        } else if (girls.size() < PAGE_COUNT) {
            mRecyclerView.noMore();
        } else {
            mRecyclerView.loadMoreComplete();
            page++;
        }
    }
}
