package com.wpq.sample.custom_recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wpq.sample.custom_recyclerview.adapter.LinearLayoutManagerAdapter;
import com.wpq.sample.custom_recyclerview.api.RetrofitService;
import com.wpq.sample.custom_recyclerview.bean.GanHuo;
import com.wpq.sample.custom_recyclerview.recyclerview.MyRecyclerView;
import com.wpq.sample.custom_recyclerview.recyclerview.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wpq
 * @version 1.0
 */
public class LinearLayoutManagerActivity extends AppCompatActivity {

    public static final String TAG = LinearLayoutManagerActivity.class.getSimpleName();
    /** 分页设置每页10条 */
    public static final int PAGE_COUNT = 10;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView)
    MyRecyclerView mRecyclerView;

    private LinearLayoutManagerAdapter mAdapter;
    private List<GanHuo.Result> mList = new ArrayList<>();

    private int page = 50; // 当前用的接口最多512条数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linearlayoutmanager);
        ButterKnife.bind(this);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1; // gank.io 0和1一样，所以从1开始
                showTime(true);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLoadMoreEnabled(true);
        mRecyclerView.setOnLoadListener(new MyRecyclerView.OnLoadListener() {
            @Override
            public void onLoadMore() {
                showTime(false);
            }
        });
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            protected void onItemClick(RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                try {
                    GanHuo.Result result = mList.get(position - 2);
                    Toast.makeText(LinearLayoutManagerActivity.this, "单击第" + position + "项：" + result.getDesc(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
            }

            @Override
            protected void onItemLongClick(RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                Toast.makeText(LinearLayoutManagerActivity.this, "长按 " + position, Toast.LENGTH_SHORT).show();
            }
        });

        View header0 = new HeaderAndFooterView(this, 0xff235840, "header0");
        mRecyclerView.addHeaderView(header0);
        View header1 = new HeaderAndFooterView(this, 0xff840395, "header1");
        mRecyclerView.addHeaderView(header1);

        mAdapter = new LinearLayoutManagerAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

        showTime(false);
    }

    /**
     * 一大波美女即将登场
     * @param isRefresh 刷新 or 加载更多
     */
    private void showTime(final boolean isRefresh) {
        // 使用gank.io api 福利接口：http://gank.io/api/data/福利/{每页条数}/页数
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);
        Call<GanHuo> call = service.getGanHuo("福利", PAGE_COUNT, page);
        call.enqueue(new Callback<GanHuo>() {
            @Override
            public void onResponse(Call<GanHuo> call, Response<GanHuo> response) {
                Log.e(TAG, response.code() + ", " + response.isSuccessful() + ", " + response.message());
                if (response.isSuccessful()) {
                    Log.e(TAG, response.body() + "");
                    GanHuo ganhuo = response.body();

                    if (isRefresh) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mList.clear();
                        mAdapter.notifyDataSetChanged();
                    }
                    mList.addAll(ganhuo.getResults());
//                    mAdapter.notifyDataSetChanged();
                    mAdapter.notifyItemRangeInserted(mRecyclerView.getHeadersCount() + mList.size(), ganhuo.getResults().size());
                    if (mList.size() < PAGE_COUNT) {
                        mRecyclerView.noNeedToLoadMore();
                    } else if (ganhuo.getResults().size() < PAGE_COUNT) {
                        mRecyclerView.noMore();
                    } else {
                        mRecyclerView.loadMoreComplete();
                        page++;
                    }
                }
            }

            @Override
            public void onFailure(Call<GanHuo> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                mRecyclerView.loadMoreError();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_linearlayoutmanager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_more_than_one_page: // 一页以上
                page = 50;
                showTime(true);
                break;
            case R.id.action_not_enough_one_page: // 不足一页
                page = 52;
                showTime(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
