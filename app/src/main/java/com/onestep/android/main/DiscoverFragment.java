package com.onestep.android.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onestep.android.R;
import com.onestep.android.common.base.BaseFragment;
import com.onestep.android.discover.DiscoverActivity;
import com.onestep.android.discover.HotFoodActivity;
import com.onestep.android.discover.HotPlacesActivity;
import com.onestep.android.discover.RouteActivity;
import com.onestep.android.main.model.GuideItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lebron on 17-4-17.
 */

public class DiscoverFragment extends BaseFragment {
    public static final String[] DISCOVER_COVER_TITLE = new String[]{"丽江五大热门景点", "本土特色美食", "丽江深度四日游"};
    public static final int[] DISCOVER_COVER_IMG = new int[]{R.mipmap.lijianggucheng, R.mipmap.food_baba1, R.mipmap.route_mufu};
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
                switch (i) {
                    case 0:
                        HotPlacesActivity.start(getActivity());
                        break;
                    case 1:
                        HotFoodActivity.start(getActivity());
                        break;
                    case 2:
                        RouteActivity.start(getActivity());
                        break;
                }
            }
        });
        quickAdapter.addHeaderView(getHeaderView());
        mRecyclerView.setAdapter(quickAdapter);

    }

    private View getHeaderView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.discover_header_view, null);
        view.findViewById(R.id.discover_attraction_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiscoverActivity.start(getActivity(), 0);
            }
        });
        view.findViewById(R.id.discover_food_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiscoverActivity.start(getActivity(), 1);
            }
        });
        view.findViewById(R.id.discover_traffic_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiscoverActivity.start(getActivity(), 2);
            }
        });
        return view;
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            GuideItem item = new GuideItem();
            item.setTitle(DISCOVER_COVER_TITLE[i]);
            item.setCoverId(DISCOVER_COVER_IMG[i]);
            mDatas.add(item);
        }
    }
}
