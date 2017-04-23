package com.onestep.android;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.onestep.android.common.util.PicassoImageLoader;

import cn.bmob.v3.Bmob;

/**
 * Created by lebron on 17-4-17.
 */

public class MainApplication extends Application {
    private static Application sAppContext;

    public static Application getAppContext() {
        return sAppContext;
    }

    public MainApplication() {
        sAppContext = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Bmob.initialize(this, "82021166fcdc7f4bbcfe1b565b40d270");
        SDKInitializer.initialize(getApplicationContext());
        sAppContext = this;

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }
}
