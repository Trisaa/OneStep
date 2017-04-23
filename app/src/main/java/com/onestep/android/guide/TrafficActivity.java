package com.onestep.android.guide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

public class TrafficActivity extends BaseActivity {
    public static final int[] TRAFFIC_TITLE = new int[]{R.string.traffic_airport_title, R.string.traffic_plane_title
            , R.string.traffic_bus_title, R.string.traffic_car_title, R.string.traffic_train_title};
    public static final int[] TRAFFIC_DESCRIBE = new int[]{R.string.traffic_airport_describe, R.string.traffic_plane_describe
            , R.string.traffic_bus_describe, R.string.traffic_car_describe, R.string.traffic_train_describe};
    public static final int[] TRAFFIC_URL = new int[]{-1, R.string.traffic_plane_url
            , R.string.traffic_bus_url, R.string.traffic_car_url, -1};
    public static final int[] TRAFFIC_DESCRIBE_DETAIL = new int[]{R.string.traffic_airport_describe_detail, -1, -1, -1,-1};
    private static final String[] TRAFFIC_URL_DESCRIBE = new String[]{"", "机场网址", "预订网址", "预订网址", ""};
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.traffic_recyclerview)
    RecyclerView mRecyclerView;

    private List<TrafficItem> mDatas;

    public static void start(Context context) {
        Intent intent = new Intent(context, TrafficActivity.class);
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
            protected void convert(BaseViewHolder baseViewHolder, final TrafficItem o) {
                baseViewHolder.setText(R.id.traffic_title_txv, o.getTitle());
                baseViewHolder.setText(R.id.traffic_describe_txv, o.getDescribe());
                baseViewHolder.setOnClickListener(R.id.traffic_url_txv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpToBrowser(o.getUrl());
                    }
                });
                if (TextUtils.isEmpty(o.getUrlDescribe())) {
                    baseViewHolder.getView(R.id.traffic_url_txv).setVisibility(View.GONE);
                } else {
                    baseViewHolder.setText(R.id.traffic_url_txv, o.getUrlDescribe());
                }
                if (TextUtils.isEmpty(o.getDescribeDetail())) {
                    baseViewHolder.getView(R.id.traffic_describe_detail_layout).setVisibility(View.GONE);
                } else {
                    baseViewHolder.setText(R.id.traffic_describe_detail_txv, o.getDescribeDetail());
                }
            }
        };
        mRecyclerView.setAdapter(quickAdapter);
    }

    private void jumpToBrowser(String url) {
        try {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(webIntent);
        } catch (Exception ignored) {

        }
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < TRAFFIC_TITLE.length; i++) {
            TrafficItem trafficItem = new TrafficItem();
            trafficItem.setTitle(getString(TRAFFIC_TITLE[i]));
            trafficItem.setDescribe(getString(TRAFFIC_DESCRIBE[i]));
            if (TRAFFIC_URL[i] != -1) {
                trafficItem.setUrl(getString(TRAFFIC_URL[i]));
            } else {
                trafficItem.setUrl("");
            }
            if (TRAFFIC_DESCRIBE_DETAIL[i] != -1) {
                trafficItem.setDescribeDetail(getString(TRAFFIC_DESCRIBE_DETAIL[i]));
            } else {
                trafficItem.setDescribeDetail("");
            }
            trafficItem.setUrlDescribe(TRAFFIC_URL_DESCRIBE[i]);
            mDatas.add(trafficItem);
        }
    }
}
