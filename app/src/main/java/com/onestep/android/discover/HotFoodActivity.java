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
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onestep.android.R;
import com.onestep.android.common.base.BaseActivity;
import com.onestep.android.main.model.GuideItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lebron on 17-4-19.
 */

public class HotFoodActivity extends BaseActivity {
    public static final int[] HOT_FOOD_COVER = new int[]{R.mipmap.food_baba, R.mipmap.food_liangfen
            , R.mipmap.food_xuetao};
    public static final int[] HOT_FOOD_TITLE = new int[]{R.string.food_baba, R.string.food_liangfen
            , R.string.food_xuetao};
    public static final int[] HOT_FOOD_DESCRIBE = new int[]{R.string.food_baba_describe, R.string.food_liangfen_describe
            , R.string.food_xuetao_describe};
    @BindView(R.id.hot_place_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.hot_place_cover_img)
    ImageView mCoverImageView;

    private List<GuideItem> mDatas;

    public static void start(Context context) {
        Intent intent = new Intent(context, HotFoodActivity.class);
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
        mCoverImageView.setImageResource(R.mipmap.food_liangfen);
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

            }
        });
        mRecyclerView.setAdapter(quickAdapter);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < HOT_FOOD_COVER.length; i++) {
            GuideItem item = new GuideItem();
            item.setTitle(getString(HOT_FOOD_TITLE[i]));
            item.setCoverId(HOT_FOOD_COVER[i]);
            item.setDescribe(getString(HOT_FOOD_DESCRIBE[i]));
            mDatas.add(item);
        }

    }
}
