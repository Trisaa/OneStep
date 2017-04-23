package com.onestep.android.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.view.SimpleDraweeView;
import com.onestep.android.R;
import com.onestep.android.account.UserActivity;
import com.onestep.android.common.base.BaseActivity;
import com.onestep.android.common.util.PreferencesHelper;
import com.onestep.android.search.SearchActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.img_user_icon)
    SimpleDraweeView mUserIconView;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager_main)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private MainAdapter mMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToobar();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserIconView.setImageURI(PreferencesHelper.getInstance().getIcon());
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            SearchActivity.start(MainActivity.this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mMainAdapter = new MainAdapter(getSupportFragmentManager(), new String[]{getResources().getString(R.string.tab_name1), getResources().getString(R.string.tab_name2)});
        mViewPager.setAdapter(mMainAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initToobar() {
        this.setSupportActionBar(mToolbar);
        ActionBar ab = this.getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(false);
            ab.setDisplayShowTitleEnabled(false);
        }
    }

    @OnClick(R.id.img_user_icon)
    public void toUserActivity() {
        UserActivity.start(this);
    }

    public class MainAdapter extends FragmentPagerAdapter {
        private String[] tabs;

        public MainAdapter(FragmentManager fm, String[] tabs) {
            super(fm);
            this.tabs = tabs;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new GuideFragment();
                    break;
                case 1:
                    fragment = new DiscoverFragment();
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
