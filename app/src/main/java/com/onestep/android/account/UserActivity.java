package com.onestep.android.account;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.onestep.android.R;
import com.onestep.android.account.model.UserModel;
import com.onestep.android.common.base.BaseActivity;
import com.onestep.android.common.util.PreferencesHelper;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import rx.functions.Action1;

/**
 * Created by lebron on 17-4-18.
 */

public class UserActivity extends BaseActivity {
    public static final int REQUEST_CODE_SELECT = 23;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.user_icon_img)
    SimpleDraweeView mUserIcon;
    @BindView(R.id.user_describe_txv)
    TextView mDescribeView;
    @BindView(R.id.user_nickname_txv)
    TextView mNickNameView;
    @BindView(R.id.user_gender_txv)
    TextView mGenderView;
    @BindView(R.id.user_birthday_txv)
    TextView mBirthCityView;
    @BindView(R.id.user_phone_txv)
    TextView mPhoneView;
    @BindView(R.id.user_logout_btn)
    Button mLogoutBtn;

    private PreferencesHelper mHelper = PreferencesHelper.getInstance();
    private UserModel mUserModel;


    public static void start(Context context) {
        Intent intent = new Intent(context, UserActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initToobar();
        initData();
    }

    @Subscribe
    public void getUserModel(UserModel userModel) {
        if (userModel != null) {
            mUserModel = userModel;
            mHelper.setIcon(mUserModel.getUserIcon());
            updateUserInfoState();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_user;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                uploadPic(images.get(0).path);
                Log.i("Lebron", " size " + images.size() + " path " + images.get(0).path);
            } else {
                Toast.makeText(this, "未选择任何图片", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initToobar() {
        this.setSupportActionBar(mToolbar);
        ActionBar ab = this.getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initData() {
        mLogoutBtn.setVisibility(mHelper.getUserLogined() ? View.VISIBLE : View.GONE);
        if (mHelper.getUserLogined()) {
            UserModel userModel = new UserModel();
            userModel.setPhone(mHelper.getPhone());
            new ILoginImpl().doLogin(userModel, false, new ILogin.LoginResponseListener() {
                @Override
                public void OnLoginResponseSuccess(UserModel user) {
                    mUserModel = user;
                    updateUserInfoState();
                }

                @Override
                public void onResponseFailed() {
                    mHelper.Clear();
                    Toast.makeText(UserActivity.this, "登录失效，请重新登录", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateUserInfoState() {
        mNickNameView.setText(mUserModel.getUserName());
        mUserIcon.setImageURI(mUserModel.getUserIcon());
        mGenderView.setText(mUserModel.getGender());
        mBirthCityView.setText(mUserModel.getCity());
        mPhoneView.setText(mUserModel.getPhone());
        if (mHelper.getUserLogined()) {
            mDescribeView.setText(TextUtils.isEmpty(mUserModel.getDescribe()) ? getString(R.string.user_no_describe) : mUserModel.getDescribe());
        } else {
            mDescribeView.setText(R.string.user_need_login);
        }
        mLogoutBtn.setVisibility(mHelper.getUserLogined() ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.user_icon_img)
    public void clickIcon() {
        if (!mHelper.getUserLogined()) {
            doLogin();
        } else {
            new RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean grant) {
                    if (grant) {
                        Intent intent = new Intent(UserActivity.this, ImageGridActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_SELECT);
                    } else {
                        Toast.makeText(UserActivity.this, "未获取所需权限", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @OnClick(R.id.user_describe_edit_btn)
    public void updateDescribe() {
        if (mHelper.getUserLogined()) {
            showInputDialog(new TextEditable() {
                @Override
                public boolean isTextEditable(int length) {
                    return length > 0;
                }

                @Override
                public int getCurrentLength() {
                    return mDescribeView.getText().length();
                }

                @Override
                public String getTitleText() {
                    return "签名";
                }

                @Override
                public String getContentText() {
                    return mDescribeView.getText().toString();
                }

                @Override
                public void submit(String value) {
                    mUserModel.setDescribe(value);
                    new ILoginImpl().doUpdate(mUserModel);
                    mDescribeView.setText(value);
                }
            });
        } else {
            doLogin();
        }
    }

    private void doLogin() {
        LoginActivity.start(this);
    }

    @OnClick(R.id.user_logout_btn)
    public void logout() {
        if (mHelper != null) {
            mHelper.Clear();
            mUserModel.clear();
            updateUserInfoState();
        }
    }

    @OnClick(R.id.user_friend_layout)
    public void toFriend() {
        //FriendActivity.start(this);
    }

    @OnClick(R.id.user_nickname_layout)
    public void updateNickName() {
        if (mHelper.getUserLogined()) {
            showInputDialog(new TextEditable() {
                @Override
                public boolean isTextEditable(int length) {
                    return length > 0;
                }

                @Override
                public int getCurrentLength() {
                    return mUserModel.getUserName() == null ? 0 : mUserModel.getUserName().length();
                }

                @Override
                public String getTitleText() {
                    return "昵称";
                }

                @Override
                public String getContentText() {
                    return mUserModel.getUserName() == null ? "" : mUserModel.getUserName();
                }

                @Override
                public void submit(String value) {
                    mUserModel.setUserName(value);
                    new ILoginImpl().doUpdate(mUserModel);
                    mNickNameView.setText(value);
                }
            });
        } else {
            doLogin();
        }
    }

    @OnClick(R.id.user_birthday_layout)
    public void updateCity() {
        if (mHelper.getUserLogined()) {
            showInputDialog(new TextEditable() {
                @Override
                public boolean isTextEditable(int length) {
                    return length > 0;
                }

                @Override
                public int getCurrentLength() {
                    return mUserModel.getCity() == null ? 0 : mUserModel.getCity().length();
                }

                @Override
                public String getTitleText() {
                    return "城市";
                }

                @Override
                public String getContentText() {
                    return mUserModel.getCity() == null ? "" : mUserModel.getCity();
                }

                @Override
                public void submit(String value) {
                    mUserModel.setCity(value);
                    new ILoginImpl().doUpdate(mUserModel);
                    mBirthCityView.setText(value);
                }
            });
        } else {
            doLogin();
        }
    }

    @OnClick(R.id.user_phone_layout)
    public void updatePhone() {
        if (mHelper.getUserLogined()) {
            Toast.makeText(UserActivity.this, "手机号不可更改", Toast.LENGTH_SHORT).show();
        } else {
            doLogin();
        }
    }

    @OnClick(R.id.user_gender_layout)
    public void clickGender() {
        if (mHelper.getUserLogined()) {
            showSelectGenderDialog();
        } else {
            doLogin();
        }
    }


    private void showInputDialog(final TextEditable editable) {
        final View view = LayoutInflater.from(this).inflate(R.layout.common_input_dialog, null);
        TextView title = (TextView) view.findViewById(R.id.account_update_title_txt);
        final EditText editText = (EditText) view.findViewById(R.id.account_update_edt);
        final TextView accountSubmitOk = (TextView) view.findViewById(R.id.account_update_submit_txt);
        if (editText.getText() != null) {
            accountSubmitOk.setTextColor(ContextCompat.getColor(this, R.color.account_update_submit));
        }
        editText.setText(editable.getContentText());
        title.setText(editable.getTitleText());
        editText.setSelection(editable.getCurrentLength());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.account_update_submit_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editable.isTextEditable(editText.length())) {
                    editable.submit(editText.getText().toString());
                    alertDialog.dismiss();
                }
            }
        });

        alertDialog.show();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void showSelectGenderDialog() {
        final View view = LayoutInflater.from(this).inflate(R.layout.common_select_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.user_male_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserModel.setGender("男");
                mGenderView.setText(mUserModel.getGender());
                new ILoginImpl().doUpdate(mUserModel);
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.user_female_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserModel.setGender("女");
                mGenderView.setText(mUserModel.getGender());
                new ILoginImpl().doUpdate(mUserModel);
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private interface TextEditable {
        boolean isTextEditable(int length);

        int getCurrentLength();

        String getTitleText();

        String getContentText();

        void submit(String value);
    }

    private void uploadPic(String path) {
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    Log.i("Lebron", "上传文件成功:" + bmobFile.getFileUrl());
                    if (mUserModel != null) {
                        mUserModel.setUserIcon(bmobFile.getFileUrl());
                        new ILoginImpl().doUpdate(mUserModel);
                        mUserIcon.setImageURI(mUserModel.getUserIcon());
                        mHelper.setIcon(mUserModel.getUserIcon());
                    }
                } else {
                    Toast.makeText(UserActivity.this, "图片上传失败，请重试", Toast.LENGTH_SHORT).show();
                    Log.i("Lebron", "上传文件失败：" + e.getMessage());
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                Log.i("Lebron", " progress " + value);
            }
        });

    }

}
