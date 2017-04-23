package com.onestep.android.discover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.onestep.android.R;
import com.onestep.android.common.base.BaseActivity;
import com.onestep.android.main.model.GuideItem;

import butterknife.BindView;

/**
 * Created by lebron on 17-4-19.
 */

public class PlaceDetailActivity extends BaseActivity {
    private static final String KEY_GUIDEITEM = "KEY_GUIDEITEM";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.hot_place_cover_img)
    ImageView mCoverImageView;
    @BindView(R.id.place_detail_title_txv)
    TextView mTitleView;
    @BindView(R.id.place_detail_describe_txv)
    TextView mDescribeView;
    @BindView(R.id.place_detail_others_txv)
    TextView mOtherView;
    @BindView(R.id.place_detail_tips_txv)
    TextView mTipsView;
    @BindView(R.id.place_detail_mapview)
    TextureMapView mMapView;
    @BindView(R.id.place_detail_locationview)
    TextView mLocationView;

    private GuideItem mGuideItem;

    public static void start(Context context, GuideItem guideItem) {
        Intent intent = new Intent(context, PlaceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_GUIDEITEM, guideItem);
        intent.putExtras(bundle);
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
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_place_detail;
    }

    private void initToobar() {
        this.setSupportActionBar(mToolbar);
        ActionBar ab = this.getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }
    }

    private void initData() {
        mGuideItem = getIntent().getParcelableExtra(KEY_GUIDEITEM);
        if (mGuideItem != null) {
            mCoverImageView.setImageResource(mGuideItem.getCoverId());
            mTitleView.setText(mGuideItem.getTitle());
            mDescribeView.setText(mGuideItem.getDescribe());
            mOtherView.setText(mGuideItem.getOthers());
            mTipsView.setText(mGuideItem.getTips());
            mLocationView.setText(mGuideItem.getTitle());
        }
    }

    private void initView() {
        mMapView.getMap().setMyLocationEnabled(true);
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mMapView.getMap().setMapStatus(msu);
        LatLng point = new LatLng(mGuideItem.getLongitude(), mGuideItem.getLatitude());
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(point);
        mMapView.getMap().animateMapStatus(update);
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(mGuideItem.getLongitude());
        builder.longitude(mGuideItem.getLatitude());
        MyLocationData locationData = builder.build();
        mMapView.getMap().setMyLocationData(locationData);
    }

}
