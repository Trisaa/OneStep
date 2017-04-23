package com.onestep.android.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onestep.android.R;
import com.onestep.android.common.base.BaseActivity;
import com.onestep.android.discover.HotPlacesActivity;
import com.onestep.android.discover.PlaceDetailActivity;
import com.onestep.android.main.model.GuideItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lebron on 17-4-19.
 */

public class SearchActivity extends BaseActivity implements TextView.OnEditorActionListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edit_search)
    EditText mSearchEdit;
    @BindView(R.id.btn_clear)
    ImageButton mClearButton;
    @BindView(R.id.search_recycler)
    RecyclerView mRecyclerView;

    private String mSearchContent;
    private List<GuideItem> mDatas = new ArrayList<>();


    public static void start(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToobar();
        addListener();
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_search;
    }

    private void initToobar() {
        this.setSupportActionBar(mToolbar);
        ActionBar ab = this.getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(false);
        }
    }

    private void addListener() {
        mSearchEdit.setOnEditorActionListener(this);
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = mSearchEdit.getText().toString();
                if (text.length() > 0) {
                    mClearButton.setVisibility(View.VISIBLE);
                } else {
                    mClearButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void searchData() {
        mDatas.clear();
        for (int i = 0; i < HotPlacesActivity.HOT_PLACES_TITLE.length; i++) {
            if (getString(HotPlacesActivity.HOT_PLACES_TITLE[i]).contains(mSearchContent) || getString(HotPlacesActivity.HOT_PLACES_DESCRIBE[i]).contains(mSearchContent)) {
                GuideItem guideItem = new GuideItem();
                guideItem.setCoverId(HotPlacesActivity.HOT_PLACES_COVER[i]);
                guideItem.setTitle(getString(HotPlacesActivity.HOT_PLACES_TITLE[i]));
                guideItem.setDescribe(getString(HotPlacesActivity.HOT_PLACES_DESCRIBE[i]));
                guideItem.setOthers(getString(HotPlacesActivity.HOT_PLACES_OTHERS[i]));
                guideItem.setTips(getString(HotPlacesActivity.HOT_PLACES_TIPS[i]));
                guideItem.setPopularity(5);
                mDatas.add(guideItem);
            }
        }

        if (mDatas.size() <= 0) {
            Toast.makeText(this, "未找到匹配项", Toast.LENGTH_SHORT).show();
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BaseQuickAdapter<GuideItem> quickAdapter2 = new BaseQuickAdapter<GuideItem>(R.layout.attraction_item, mDatas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, GuideItem o) {
                baseViewHolder.setText(R.id.attraction_name_txv, o.getTitle());
                baseViewHolder.setText(R.id.attraction_describe_txv, "地标建筑");
                baseViewHolder.setBackgroundRes(R.id.attraction_img, o.getCoverId());
                RatingBar ratingBar = baseViewHolder.getView(R.id.attraction_ratebar);
                ratingBar.setRating(o.getPopularity());
            }
        };
        quickAdapter2.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                PlaceDetailActivity.start(SearchActivity.this, mDatas.get(i));
            }
        });
        mRecyclerView.setAdapter(quickAdapter2);
    }

    @OnClick(R.id.btn_clear)
    public void clear() {
        mSearchEdit.setText("");
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            mSearchContent = mSearchEdit.getText().toString();
            searchData();
        }
        return true;
    }
}
