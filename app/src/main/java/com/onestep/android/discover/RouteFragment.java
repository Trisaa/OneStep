package com.onestep.android.discover;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onestep.android.R;
import com.onestep.android.common.base.BaseFragment;
import com.onestep.android.main.model.GuideItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lebron on 17-4-18.
 */

public class RouteFragment extends BaseFragment {
    public static final String[] GUIDE_COVER_TITLE = new String[]{"玩转丽江--丽江深度四日游"};
    public static final int[] GUIDE_COVER_IMG = new int[]{R.mipmap.route_mufu};
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
        BaseQuickAdapter<GuideItem> quickAdapter = new BaseQuickAdapter<GuideItem>(R.layout.guide_card_item, mDatas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, GuideItem o) {
                baseViewHolder.setText(R.id.guide_item_title_txv, o.getTitle());
                baseViewHolder.setBackgroundRes(R.id.guide_item_cover_img, o.getCoverId());
            }
        };
        quickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                RouteActivity.start(getActivity());
            }
        });
        mRecyclerView.setAdapter(quickAdapter);

    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < GUIDE_COVER_TITLE.length; i++) {
            GuideItem item = new GuideItem();
            item.setTitle(GUIDE_COVER_TITLE[i]);
            item.setCoverId(GUIDE_COVER_IMG[i]);
            mDatas.add(item);
        }
    }
}
