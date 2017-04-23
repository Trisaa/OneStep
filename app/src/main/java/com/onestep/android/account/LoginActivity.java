package com.onestep.android.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.onestep.android.R;
import com.onestep.android.account.model.UserModel;
import com.onestep.android.common.base.BaseActivity;
import com.onestep.android.common.util.PhoneUtils;
import com.onestep.android.common.util.PreferencesHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lebron on 17-4-20.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.account_email_login_email_input_txt)
    EditText mPhoneEdit;
    @BindView(R.id.account_email_login_password_input_txt)
    EditText mPasswordEdit;
    @BindView(R.id.loading_progress)
    ProgressBar mProgressbar;

    private UserModel mUserModel = new UserModel();
    private PreferencesHelper mHelper = PreferencesHelper.getInstance();

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_login;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initToobar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initToobar() {
        this.setSupportActionBar(mToolbar);
        ActionBar ab = this.getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @OnClick(R.id.account_email_login_btn)
    public void login() {
        String phone = mPhoneEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        boolean isPhoneOK = PhoneUtils.isMobile(phone);
        boolean isPasswordOK = !TextUtils.isEmpty(password);
        if (!isPhoneOK) {
            mPhoneEdit.setError("请输入正确的手机号");
        }
        if (!isPasswordOK) {
            mPasswordEdit.setError("密码不能为空");
        }
        if (isPhoneOK && isPasswordOK) {
            mUserModel.setPhone(phone);
            mUserModel.setPassword(password);
            mProgressbar.setVisibility(View.VISIBLE);
            new ILoginImpl().doLogin(mUserModel, true, new ILogin.LoginResponseListener() {
                @Override
                public void OnLoginResponseSuccess(UserModel user) {
                    mHelper.setObjectId(user.getObjectId());
                    mHelper.setPhone(user.getPhone());
                    mHelper.setUserLogined(true);
                    mUserModel = user;
                    EventBus.getDefault().post(mUserModel);
                    finish();
                    mProgressbar.setVisibility(View.GONE);
                }

                @Override
                public void onResponseFailed() {
                    mProgressbar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "登录失败，帐号或密码有误", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @OnClick(R.id.account_email_login_register_txt)
    public void register() {
        RegisterActivity.start(this);
    }

    @Subscribe
    public void getUserModel(String event) {
        if (event != null && event.equals("close")) {
            finish();
        }
    }
}
