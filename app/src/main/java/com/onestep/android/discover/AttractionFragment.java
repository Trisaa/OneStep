package com.onestep.android.discover;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onestep.android.R;
import com.onestep.android.common.base.BaseFragment;
import com.onestep.android.main.model.GuideItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lebron on 17-4-18.
 */

public class AttractionFragment extends BaseFragment {
    private static final String[] GUIDE_COVER_TITLE = new String[]{"丽江五大热门景点"};
    private static final int[] GUIDE_COVER_IMG = new int[]{R.mipmap.lijianggucheng};
    @BindView(R.id.attraction_recycler1)
    RecyclerView mRecyclerView1;
    @BindView(R.id.attraction_recycler2)
    RecyclerView mRecyclerView2;
    @BindView(R.id.attraction_btn1)
    TextView mButton1;
    @BindView(R.id.attraction_btn2)
    TextView mButton2;

    private List<GuideItem> mDatas1, mDatas2;

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_attraction;
    }

    @Override
    protected void initViewsAndData(View view) {
        initData();
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        BaseQuickAdapter<GuideItem> quickAdapter1 = new BaseQuickAdapter<GuideItem>(R.layout.guide_card_item, mDatas1) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, GuideItem o) {
                baseViewHolder.setText(R.id.guide_item_title_txv, o.getTitle());
                baseViewHolder.setBackgroundRes(R.id.guide_item_cover_img, o.getCoverId());
            }
        };
        quickAdapter1.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                HotPlacesActivity.start(getActivity());
            }
        });
        mRecyclerView1.setAdapter(quickAdapter1);

        mRecyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        BaseQuickAdapter<GuideItem> quickAdapter2 = new BaseQuickAdapter<GuideItem>(R.layout.attraction_item, mDatas2) {
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
                PlaceDetailActivity.start(getActivity(), mDatas2.get(i));
            }
        });
        mRecyclerView2.setAdapter(quickAdapter2);
    }

    private void initData() {
        mDatas1 = new ArrayList<>();
        mDatas2 = new ArrayList<>();
        for (int i = 0; i < GUIDE_COVER_TITLE.length; i++) {
            GuideItem item = new GuideItem();
            item.setTitle(GUIDE_COVER_TITLE[i]);
            item.setCoverId(GUIDE_COVER_IMG[i]);
            mDatas1.add(item);
        }

        GuideItem guideItem = new GuideItem();
        guideItem.setTitle(getString(R.string.place_lijianggucheng));
        guideItem.setDescribe(getString(R.string.attraction_lijianggucheng_detail));
        guideItem.setOthers(getString(R.string.attraction_lijianggucheng_others));
        guideItem.setTips(getString(R.string.attraction_lijianggucheng_tips));
        guideItem.setLatitude(100.2432);
        guideItem.setLongitude(26.8765);
        guideItem.setCoverId(R.mipmap.lijianggucheng1);
        guideItem.setPopularity(5);
        mDatas2.add(guideItem);
    }

    @OnClick(R.id.attraction_btn1)
    public void clickButton1() {
        mRecyclerView1.setVisibility(View.VISIBLE);
        mRecyclerView2.setVisibility(View.GONE);
        mButton1.setTextColor(getResources().getColor(R.color.attraction_selected));
        mButton1.setBackgroundResource(R.drawable.common_green_btn_bg);
        mButton2.setTextColor(getResources().getColor(R.color.attraction_unselected));
        mButton2.setBackgroundResource(android.R.color.transparent);
    }

    @OnClick(R.id.attraction_btn2)
    public void clickButton2() {
        mRecyclerView2.setVisibility(View.VISIBLE);
        mRecyclerView1.setVisibility(View.GONE);
        mButton2.setTextColor(getResources().getColor(R.color.attraction_selected));
        mButton2.setBackgroundResource(R.drawable.common_green_btn_bg);
        mButton1.setTextColor(getResources().getColor(R.color.attraction_unselected));
        mButton1.setBackgroundResource(android.R.color.transparent);
    }
}
