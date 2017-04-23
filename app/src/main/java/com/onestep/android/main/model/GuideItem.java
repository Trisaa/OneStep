package com.onestep.android.main.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lebron on 17-4-18.
 */

public class GuideItem implements Parcelable {
    private String title;
    private String describe;
    private String others;
    private String tips;
    private float popularity;
    private int coverId;
    private int showDays;
    private double latitude;
    private double longitude;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCoverId() {
        return coverId;
    }

    public void setCoverId(int coverId) {
        this.coverId = coverId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int isShowDays() {
        return showDays;
    }

    public void setShowDays(int showDays) {
        this.showDays = showDays;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public GuideItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.describe);
        dest.writeString(this.others);
        dest.writeString(this.tips);
        dest.writeFloat(this.popularity);
        dest.writeInt(this.coverId);
        dest.writeInt(this.showDays);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    protected GuideItem(Parcel in) {
        this.title = in.readString();
        this.describe = in.readString();
        this.others = in.readString();
        this.tips = in.readString();
        this.popularity = in.readFloat();
        this.coverId = in.readInt();
        this.showDays = in.readInt();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public static final Creator<GuideItem> CREATOR = new Creator<GuideItem>() {
        @Override
        public GuideItem createFromParcel(Parcel source) {
            return new GuideItem(source);
        }

        @Override
        public GuideItem[] newArray(int size) {
            return new GuideItem[size];
        }
    };
}
