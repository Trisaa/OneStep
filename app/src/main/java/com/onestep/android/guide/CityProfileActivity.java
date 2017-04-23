package com.onestep.android.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.onestep.android.R;
import com.onestep.android.common.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by lebron on 17-4-18.
 */

public class CityProfileActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static void start(Context context) {
        Intent intent = new Intent(context, CityProfileActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToobar();
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_city_profile;
    }

    private void initToobar() {
        this.setSupportActionBar(mToolbar);
        ActionBar ab = this.getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
}
