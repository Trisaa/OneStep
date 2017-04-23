package com.onestep.android.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onestep.android.R;
import com.onestep.android.common.base.BaseActivity;
import com.onestep.android.guide.model.TrafficItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lebron on 17-4-18.
 */

public class ConsumptionActivity extends BaseActivity {
    public static final int[] TRAFFIC_TITLE = new int[]{R.string.consumption_life_title, R.string.consumption_entertainment_title};
    public static final int[] TRAFFIC_DESCRIBE = new int[]{R.string.consumption_life_describe, R.string.consumption_entertainment_describe};
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.traffic_recyclerview)
    RecyclerView mRecyclerView;

    private List<TrafficItem> mDatas;

    public static void start(Context context) {
        Intent intent = new Intent(context, ConsumptionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initToobar();
        initView();
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_traffic;
    }

    private void initToobar() {
        this.setSupportActionBar(mToolbar);
        ActionBar ab = this.getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BaseQuickAdapter<TrafficItem> quickAdapter = new BaseQuickAdapter<TrafficItem>(R.layout.traffic_item, mDatas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, TrafficItem o) {
                baseViewHolder.setText(R.id.traffic_title_txv, o.getTitle());
                baseViewHolder.setText(R.id.traffic_describe_txv, o.getDescribe());
                baseViewHolder.getView(R.id.traffic_url_txv).setVisibility(View.GONE);
            }
        };
        mRecyclerView.setAdapter(quickAdapter);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < TRAFFIC_TITLE.length; i++) {
            TrafficItem trafficItem = new TrafficItem();
            trafficItem.setTitle(getString(TRAFFIC_TITLE[i]));
            trafficItem.setDescribe(getString(TRAFFIC_DESCRIBE[i]));
            mDatas.add(trafficItem);
        }
    }
}
