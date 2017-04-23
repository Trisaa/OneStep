package com.onestep.android.discover;

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
import com.onestep.android.main.model.GuideItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lebron on 17-4-18.
 */

public class HotPlacesActivity extends BaseActivity {
    public static final int[] HOT_PLACES_COVER = new int[]{R.mipmap.lijianggucheng, R.mipmap.shuheguzhen
            , R.mipmap.yulongxueshan, R.mipmap.lanyuegu, R.mipmap.luguhu};
    public static final int[] HOT_PLACES_TITLE = new int[]{R.string.place_lijianggucheng, R.string.place_shuheguzhen
            , R.string.place_yulongxueshan, R.string.place_lanyuegu, R.string.place_luguhu};
    public static final int[] HOT_PLACES_DESCRIBE = new int[]{R.string.place_describe_lijianggucheng, R.string.place_describe_shuheguzhen
            , R.string.place_describe_yulongxueshan, R.string.place_describe_lanyuegu, R.string.place_describe_luguhu};
    public static final int[] HOT_PLACES_OTHERS = new int[]{R.string.attraction_lijianggucheng_others, R.string.attraction_shuheguzhen_others
            , R.string.attraction_yulongxueshan_others, R.string.attraction_lanyuegu_others, R.string.attraction_luguhu_others};
    public static final int[] HOT_PLACES_TIPS = new int[]{R.string.attraction_lijianggucheng_tips, R.string.attraction_shuheguzhen_tips
            , R.string.attraction_yulongxueshan_tips, R.string.attraction_lanyuegu_tips, R.string.attraction_luguhu_tips};
    public static final double[] HOT_PLACES_LATITUDE = new double[]{100.2432, 100.2120, 100.1856, 100.2512, 100.7802};
    public static final double[] HOT_PLACES_LONGITUDE = new double[]{26.8765, 26.9241, 27.1023, 27.1316, 27.7483};
    @BindView(R.id.hot_place_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private List<GuideItem> mDatas;

    public static void start(Context context) {
        Intent intent = new Intent(context, HotPlacesActivity.class);
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BaseQuickAdapter<GuideItem> quickAdapter = new BaseQuickAdapter<GuideItem>(R.layout.hot_place_item, mDatas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, GuideItem o) {
                baseViewHolder.setText(R.id.hot_place_item_name_txv, o.getTitle());
                baseViewHolder.setText(R.id.hot_place_item_describe_txv, o.getDescribe());
                baseViewHolder.setBackgroundRes(R.id.hot_place_item_cover_img, o.getCoverId());
            }
        };
        quickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                PlaceDetailActivity.start(HotPlacesActivity.this, mDatas.get(i));
            }
        });
        mRecyclerView.setAdapter(quickAdapter);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < HOT_PLACES_COVER.length; i++) {
            GuideItem item = new GuideItem();
            item.setTitle(getString(HOT_PLACES_TITLE[i]));
            item.setCoverId(HOT_PLACES_COVER[i]);
            item.setDescribe(getString(HOT_PLACES_DESCRIBE[i]));
            item.setOthers(getString(HOT_PLACES_OTHERS[i]));
            item.setTips(getString(HOT_PLACES_TIPS[i]));
            item.setLatitude(HOT_PLACES_LATITUDE[i]);
            item.setLongitude(HOT_PLACES_LONGITUDE[i]);
            mDatas.add(item);
        }

    }
}
