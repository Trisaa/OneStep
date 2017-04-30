package com.onestep.android.discover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onestep.android.R;
import com.onestep.android.account.FriendActivity;
import com.onestep.android.common.base.BaseActivity;
import com.onestep.android.main.model.GuideItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lebron on 17-4-19.
 */

public class RouteActivity extends BaseActivity {
    private static final int[] HOT_PLACES_COVER = new int[]{R.mipmap.shizishan, R.mipmap.route_mufu, R.mipmap.sifangjie
            , R.mipmap.bingchuangongyuan, R.mipmap.yunshanpin, R.mipmap.lanyuegu
            , R.mipmap.shuheguzhen, R.mipmap.shuhesifangjie, R.mipmap.qinglongqiao, R.mipmap.longquansi, R.mipmap.chamagudao
            , R.mipmap.shidigongyuan};
    private static final int[] HOT_PLACES_TITLE = new int[]{R.string.route_shizishan, R.string.route_mufu
            , R.string.route_sifangjie, R.string.route_bingchuangongyuan, R.string.route_yunshanpin, R.string.route_lanyuegu
            , R.string.route_shuheguzhen, R.string.route_shuhesifangjie, R.string.route_qinglongqiao, R.string.route_longquansi, R.string.route_chamagudao
            , R.string.route_shidigongyuan};
    private static final int[] HOT_PLACES_DESCRIBE = new int[]{R.string.route_shizishan_describe, R.string.route_mufu_describe
            , R.string.route_sifangjie_describe, R.string.route_bingchuangongyuan_describe, R.string.route_yunshanpin_describe, R.string.route_lanyuegu_describe
            , R.string.route_shuheguzhen_describe, R.string.route_shuhesifangjie_describe, R.string.route_qinglongqiao_describe, R.string.route_longquansi_describe, R.string.route_chamagudao_describe
            , R.string.route_shidigongyuan_describe};
    private static final int[] HOT_PLACES_OTHERS = new int[]{R.string.route_shizishan_others, R.string.route_mufu_others, R.string.route_sifangjie_others
            , R.string.route_bingchuangongyuan_others, R.string.route_yunshanpin_others, R.string.route_lanyuegu_others
            , R.string.route_shuheguzhen_others, R.string.route_shuhesifangjie_others, R.string.route_qinglongqiao_others, R.string.route_longquansi_others, R.string.route_chamagudao_others
            , R.string.route_shidigongyuan_others};
    private static final int[] HOT_PLACES_TIPS = new int[]{R.string.route_shizishan_tips, R.string.route_mufu_tips, R.string.route_sifangjie_tips};
    public static final double[] HOT_PLACES_LATITUDE = new double[]{101.0629, 100.3410, 100.2411, 100.2609, 100.2414, 100.2512, 100.2120, 100.2109, 100.2103, 100.2102, 100.2118, 100.1526};
    public static final double[] HOT_PLACES_LONGITUDE = new double[]{26.4432, 26.8750, 26.8786, 27.1047, 27.1356, 27.1316, 26.9241, 26.9277, 26.9280, 26.9310, 26.9283, 26.8680};
    @BindView(R.id.hot_place_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.hot_place_cover_img)
    ImageView mCoverImageView;

    private List<GuideItem> mDatas;

    public static void start(Context context) {
        Intent intent = new Intent(context, RouteActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToobar();
        initData();
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_location, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_location) {
            RouteMapActivity.start(this);
        } else if (item.getItemId() == R.id.action_local) {
            FriendActivity.start(this);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected int getContentViewResId() {
        return R.layout.activity_hot_places;
    }

    private void initToobar() {
        this.setSupportActionBar(mToolbar);
        ActionBar ab = this.getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }
    }

    private void initView() {
        mCoverImageView.setImageResource(R.mipmap.sifangjie);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BaseQuickAdapter<GuideItem> quickAdapter = new BaseQuickAdapter<GuideItem>(R.layout.route_item, mDatas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, GuideItem o) {
                baseViewHolder.setText(R.id.hot_place_item_name_txv, o.getTitle());
                baseViewHolder.setText(R.id.hot_place_item_describe_txv, o.getDescribe());
                baseViewHolder.setBackgroundRes(R.id.hot_place_item_cover_img, o.getCoverId());
                if (o.isShowDays() != 0) {
                    baseViewHolder.getView(R.id.hot_place_item_days_txv).setVisibility(View.VISIBLE);
                    baseViewHolder.getView(R.id.hot_place_item_days_divider).setVisibility(View.VISIBLE);
                    baseViewHolder.setText(R.id.hot_place_item_days_txv, "第" + o.isShowDays() + "天");
                    Log.i("Lebron", " " + o.isShowDays());
                } else {
                    baseViewHolder.getView(R.id.hot_place_item_days_txv).setVisibility(View.GONE);
                    baseViewHolder.getView(R.id.hot_place_item_days_divider).setVisibility(View.GONE);
                }
            }
        };
        quickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                PlaceDetailActivity.start(RouteActivity.this, mDatas.get(i));
            }
        });
        mRecyclerView.setAdapter(quickAdapter);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        int day = 1;
        for (int i = 0; i < HOT_PLACES_COVER.length; i++) {
            GuideItem item = new GuideItem();
            item.setTitle(getString(HOT_PLACES_TITLE[i]));
            item.setCoverId(HOT_PLACES_COVER[i]);
            item.setDescribe(getString(HOT_PLACES_DESCRIBE[i]));
            item.setOthers(getString(HOT_PLACES_OTHERS[i]));
            item.setTips(getString(HOT_PLACES_TIPS[0]));
            item.setLatitude(HOT_PLACES_LATITUDE[i]);
            item.setLongitude(HOT_PLACES_LONGITUDE[i]);
            if (i == 0 || i == 3 || i == 6 || i == 11) {
                item.setShowDays(day);
                day++;
            } else {
                item.setShowDays(0);
            }
            mDatas.add(item);
        }

    }
}
