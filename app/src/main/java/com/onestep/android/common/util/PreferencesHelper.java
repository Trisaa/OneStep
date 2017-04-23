package com.onestep.android.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.onestep.android.MainApplication;

/**
 * Created by lebron on 17-4-19.
 */

public class PreferencesHelper {
    private static final String KEY_USER_LOGINED = "KEY_USER_LOGINED";
    private static final String KEY_USER_ICON = "KEY_USER_ICON";
    private static final String KEY_USER_PHONE = "KEY_USER_PHONE";
    private static final String KEY_USER_OBJECTID = "KEY_USER_OBJECTID";

    private final static SharedPreferences mPref = MainApplication.getAppContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
    private static PreferencesHelper mInstance = null;

    public static PreferencesHelper getInstance() {
        if (mInstance == null) {
            synchronized (PreferencesHelper.class) {
                if (mInstance == null) {
                    mInstance = new PreferencesHelper();
                }
            }
        }
        return mInstance;
    }

    private PreferencesHelper() {
    }

    public void Clear() {
        mPref.edit().clear().apply();
    }

    public boolean getUserLogined() {
        return mPref.getBoolean(KEY_USER_LOGINED, false);
    }

    public void setUserLogined(boolean logined) {
        mPref.edit().putBoolean(KEY_USER_LOGINED, logined).apply();
    }

    public String getIcon() {
        return mPref.getString(KEY_USER_ICON, "");
    }

    public void setIcon(String icon) {
        mPref.edit().putString(KEY_USER_ICON, icon).apply();
    }

    public String getPhone() {
        return mPref.getString(KEY_USER_PHONE, "");
    }

    public void setPhone(String phone) {
        mPref.edit().putString(KEY_USER_PHONE, phone).apply();
    }

    public String getObjectId() {
        return mPref.getString(KEY_USER_OBJECTID, "");
    }

    public void setObjectId(String id) {
        mPref.edit().putString(KEY_USER_OBJECTID, id).apply();
    }

}
