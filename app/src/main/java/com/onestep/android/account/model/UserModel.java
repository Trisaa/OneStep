package com.onestep.android.account.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by lebron on 17-4-19.
 */

public class UserModel extends BmobObject {
    private String userId;
    private String userName;
    private String password;
    private String userIcon;
    private String gender;
    private String city;
    private String phone;
    private String describe;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void clear() {
        userName = "";
        userIcon = "";
        gender = "";
        city = "";
        phone = "";
        password = "";
        describe = "";
    }
}
