package com.onestep.android.account;


import com.onestep.android.account.model.UserModel;

/**
 * Created by lebron on 17-4-19.
 */

public interface ILogin {
    void doLogin(UserModel userModel, boolean loginOrQuery, LoginResponseListener listener);

    void doRegister(UserModel userModel, RegisterResponseListener listener);

    void doUpdate(UserModel userModel);

    interface LoginResponseListener {
        void OnLoginResponseSuccess(UserModel user);

        void onResponseFailed();
    }

    interface RegisterResponseListener {
        void OnRegisterResponseSuccess(String objectId);

        void onResponseFailed();
    }
}
