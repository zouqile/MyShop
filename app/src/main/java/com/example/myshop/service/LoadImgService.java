package com.example.myshop.service;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * Created by zouqile on 2016-03-05.
 */
public class LoadImgService {
    private Context context;
    private DisplayImageOptions optionsDefault;

    public LoadImgService(Context context) {
        this.context = context;
        optionsDefault = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true).build();
    }

    public void LoadImg(ImageView imageView, String url) {
        ImageLoader.getInstance().displayImage(url, imageView, optionsDefault);
    }
}
