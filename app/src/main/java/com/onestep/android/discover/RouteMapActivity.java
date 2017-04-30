package com.onestep.android.discover;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.onestep.android.R;
import com.onestep.android.common.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.onestep.android.discover.RouteActivity.HOT_PLACES_LATITUDE;
import static com.onestep.android.discover.RouteActivity.HOT_PLACES_LONGITUDE;

/**
 * Created by lebron on 17-4-23.
 */

public class RouteMapActivity extends BaseActivity {
    public static int[] COLORS = new int[]{Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN, Color.GRAY};
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.route_map_view)
    TextureMapView mMapView;
    @BindView(R.id.route_map_day1)
    TextView mDay1View;
    @BindView(R.id.route_map_day2)
    TextView mDay2View;
    @BindView(R.id.route_map_day3)
    TextView mDay3View;
    @BindView(R.id.route_map_day4)
    TextView mDay4View;

    private Polyline mPolyline;

    public static void start(Context context) {
        Intent intent = new Intent(context, RouteMapActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_map;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToobar();
        initView(0, 3);
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

    @OnClick(R.id.route_map_day1)
    public void clickDay1() {
        initView(0, 3);
        mDay1View.setTextColor(getResources().getColor(R.color.attraction_selected));
        mDay1View.setBackgroundResource(R.drawable.common_green_btn_bg);
        mDay2View.setTextColor(getResources().getColor(R.color.attraction_unselected));
        mDay2View.setBackgroundResource(android.R.color.transparent);
        mDay3View.setTextColor(getResources().getColor(R.color.attraction_unselected));
        mDay3View.setBackgroundResource(android.R.color.transparent);
        mDay4View.setTextColor(getResources().getColor(R.color.attraction_unselected));
        mDay4View.setBackgroundResource(android.R.color.transparent);
    }

    @OnClick(R.id.route_map_day2)
    public void clickDay2() {
        initView(3, 6);
        mDay2View.setTextColor(getResources().getColor(R.color.attraction_selected));
        mDay2View.setBackgroundResource(R.drawable.common_green_btn_bg);
        mDay1View.setTextColor(getResources().getColor(R.color.attraction_unselected));
        mDay1View.setBackgroundResource(android.R.color.transparent);
        mDay3View.setTextColor(getResources().getColor(R.color.attraction_unselected));
        mDay3View.setBackgroundResource(android.R.color.transparent);
        mDay4View.setTextColor(getResources().getColor(R.color.attraction_unselected));
        mDay4View.setBackgroundResource(android.R.color.transparent);
    }

    @OnClick(R.id.route_map_day3)
    public void clickDay3() {
        initView(6, 11);
        mDay3View.setTextColor(getResources().getColor(R.color.attraction_selected));
        mDay3View.setBackgroundResource(R.drawable.common_green_btn_bg);
        mDay2View.setTextColor(getResources().getColor(R.color.attraction_unselected));
        mDay2View.setBackgroundResource(android.R.color.transparent);
        mDay1View.setTextColor(getResources().getColor(R.color.attraction_unselected));
        mDay1View.setBackgroundResource(android.R.color.transparent);
        mDay4View.setTextColor(getResources().getColor(R.color.attraction_unselected));
        mDay4View.setBackgroundResource(android.R.color.transparent);
    }

    @OnClick(R.id.route_map_day4)
    public void clickDay4() {
        initView(11, 12);
        mDay4View.setTextColor(getResources().getColor(R.color.attraction_selected));
        mDay4View.setBackgroundResource(R.drawable.common_green_btn_bg);
        mDay2View.setTextColor(getResources().getColor(R.color.attraction_unselected));
        mDay2View.setBackgroundResource(android.R.color.transparent);
        mDay3View.setTextColor(getResources().getColor(R.color.attraction_unselected));
        mDay3View.setBackgroundResource(android.R.color.transparent);
        mDay1View.setTextColor(getResources().getColor(R.color.attraction_unselected));
        mDay1View.setBackgroundResource(android.R.color.transparent);
    }

    private void initToobar() {
        this.setSupportActionBar(mToolbar);
        ActionBar ab = this.getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView(int start, int end) {
        if (mPolyline != null) {
            mPolyline.remove();
        }
        if (start == 0) {
            MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(10.0f);
            mMapView.getMap().setMapStatus(msu);
        } else {
            MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
            mMapView.getMap().setMapStatus(msu);
        }

        LatLng point = new LatLng(HOT_PLACES_LONGITUDE[start], HOT_PLACES_LATITUDE[start]);
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(point);
        mMapView.getMap().animateMapStatus(update);

        List<LatLng> points = new ArrayList<LatLng>();
        for (int i = start; i < end; i++) {
            if (start == 11) {
                points.add(new LatLng(HOT_PLACES_LONGITUDE[i]-0.001, HOT_PLACES_LATITUDE[i]));
            }
            points.add(new LatLng(HOT_PLACES_LONGITUDE[i], HOT_PLACES_LATITUDE[i]));
        }

        OverlayOptions ooPolyline = new PolylineOptions().width(10)
                .color(Integer.valueOf(Color.GREEN)).points(points);
        mPolyline = (Polyline) mMapView.getMap().addOverlay(ooPolyline);

    }
}
