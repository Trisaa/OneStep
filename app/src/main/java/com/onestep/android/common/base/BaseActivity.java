package com.onestep.android.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by lebron on 17-4-17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentViewResId() != 0) {
            setContentView(getContentViewResId());
        }
        ButterKnife.bind(this);
    }

    @Subscribe
    public void onEvent(String event) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract int getContentViewResId();
}
