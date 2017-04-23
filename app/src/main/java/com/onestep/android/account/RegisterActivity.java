package com.onestep.android.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.onestep.android.R;
import com.onestep.android.account.model.UserModel;
import com.onestep.android.common.base.BaseActivity;
import com.onestep.android.common.util.PhoneUtils;
import com.onestep.android.common.util.PreferencesHelper;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lebron on 17-4-20.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.account_email_login_email_input_txt)
    EditText mPhoneEdit;
    @BindView(R.id.account_email_login_password_input_txt)
    EditText mPasswordEdit;
    @BindView(R.id.loading_progress)
    ProgressBar mProgressbar;
    @BindView(R.id.account_email_login_register_txt)
    TextView mRegisterView;
    @BindView(R.id.account_email_login_btn)
    Button mRegisterButton;

    private UserModel mUserModel = new UserModel();
    private PreferencesHelper mHelper = PreferencesHelper.getInstance();

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_login;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initToobar();
        mRegisterView.setVisibility(View.GONE);
        mRegisterButton.setText("注册");
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
    public void register() {
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
            mUserModel = new UserModel();
            mUserModel.setPhone(phone);
            mUserModel.setPassword(password);
            mProgressbar.setVisibility(View.VISIBLE);
            new ILoginImpl().doRegister(mUserModel, new ILogin.RegisterResponseListener() {
                @Override
                public void OnRegisterResponseSuccess(String objectId) {
                    mUserModel.setObjectId(objectId);
                    mHelper.setObjectId(objectId);
                    mHelper.setPhone(mUserModel.getPhone());
                    mHelper.setUserLogined(true);
                    EventBus.getDefault().post("close");
                    EventBus.getDefault().post(mUserModel);
                    finish();
                    mProgressbar.setVisibility(View.GONE);
                }

                @Override
                public void onResponseFailed() {
                    Toast.makeText(RegisterActivity.this, "注册失败，请检查帐号是否已经被注册", Toast.LENGTH_SHORT).show();
                    mProgressbar.setVisibility(View.GONE);
                }
            });
        }
    }
}
