package com.onestep.android.discover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.onestep.android.R;
import com.onestep.android.common.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by lebron on 17-4-18.
 */

public class DiscoverActivity extends BaseActivity {
    public static final String KEY_PAGER_POS = "KEY_PAGER_POS";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.discover_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.discover_viewpager)
    ViewPager mViewPager;

    private DiscoverAdapter mDiscoverAdapter;
    private int mCurrentPos;

    public static void start(Context context, int pos) {
        Intent intent = new Intent(context, DiscoverActivity.class);
        intent.putExtra(KEY_PAGER_POS, pos);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentPos = getIntent().getIntExtra(KEY_PAGER_POS, 0);
        initToobar();
        initView();
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_discover;
    }

    private void initToobar() {
        this.setSupportActionBar(mToolbar);
        ActionBar ab = this.getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setVisibility(View.GONE);
    }

    private void initView() {
        mDiscoverAdapter = new DiscoverAdapter(getSupportFragmentManager(), new String[]{getResources().getString(R.string.tab_attraction)
                , getResources().getString(R.string.tab_food), getResources().getString(R.string.tab_route)});
        mViewPager.setAdapter(mDiscoverAdapter);
        mViewPager.setCurrentItem(mCurrentPos);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public class DiscoverAdapter extends FragmentPagerAdapter {
        private String[] tabs;

        public DiscoverAdapter(FragmentManager fm, String[] tabs) {
            super(fm);
            this.tabs = tabs;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new AttractionFragment();
                    break;
                case 1:
                    fragment = new FoodFragment();
                    break;
                case 2:
                    fragment = new RouteFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            if (tabs != null) {
                return tabs.length;
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}
