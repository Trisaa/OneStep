package com.onestep.android.main;


import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onestep.android.R;
import com.onestep.android.common.base.BaseFragment;
import com.onestep.android.guide.CityProfileActivity;
import com.onestep.android.guide.ConsumptionActivity;
import com.onestep.android.guide.TrafficActivity;
import com.onestep.android.main.model.GuideItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lebron on 17-4-17.
 */

public class GuideFragment extends BaseFragment {
    public static final int[] GUIDE_COVER_TITLE = new int[]{R.string.city_profile_title, R.string.traffic_title, R.string.consumption_title};
    public static final int[] GUIDE_COVER_SECOND_TITLE = new int[]{R.string.city_profile_second_title, R.string.traffic_second_title, R.string.consumption_second_title};
    public static final int[] GUIDE_COVER_IMG = new int[]{R.mipmap.city_introduc, R.mipmap.traffic_guide, R.mipmap.consumption_level};
    @BindView(R.id.guide_recyclerview)
    RecyclerView mRecyclerView;

    private List<GuideItem> mDatas;

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_guide;
    }

    @Override
    protected void initViewsAndData(View view) {
        initData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BaseQuickAdapter<GuideItem> quickAdapter = new BaseQuickAdapter<GuideItem>(R.layout.main_guide_item, mDatas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, GuideItem o) {
                baseViewHolder.setText(R.id.main_guide_item_title_txv, o.getTitle());
                baseViewHolder.setText(R.id.main_guide_item_second_title_txv, o.getDescribe());
                Drawable drawable = getResources().getDrawable(o.getCoverId());
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                ((TextView) baseViewHolder.getView(R.id.main_guide_item_second_title_txv)).setCompoundDrawables(null, drawable, null, null);
            }
        };
        quickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                switch (i) {
                    case 0:
                        CityProfileActivity.start(getActivity());
                        break;
                    case 1:
                        TrafficActivity.start(getActivity());
                        break;
                    case 2:
                        ConsumptionActivity.start(getActivity());
                        break;
                }
            }
        });
        mRecyclerView.setAdapter(quickAdapter);

    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            GuideItem item = new GuideItem();
            item.setTitle(getString(GUIDE_COVER_TITLE[i]));
            item.setCoverId(GUIDE_COVER_IMG[i]);
            item.setDescribe(getString(GUIDE_COVER_SECOND_TITLE[i]));
            mDatas.add(item);
        }
    }
}
