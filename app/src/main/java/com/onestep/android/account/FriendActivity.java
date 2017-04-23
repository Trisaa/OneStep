package com.onestep.android.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.onestep.android.R;
import com.onestep.android.common.base.BaseActivity;
import com.onestep.android.discover.RouteMapActivity;

import butterknife.BindView;

/**
 * Created by lebron on 17-4-23.
 */

public class FriendActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.friend_describe)
    TextView mDescribeView;
    @BindView(R.id.user_describe_txv)
    TextView mNameView;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_friend;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, FriendActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToobar();
    }

    private void initToobar() {
        this.setSupportActionBar(mToolbar);
        ActionBar ab = this.getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
}
