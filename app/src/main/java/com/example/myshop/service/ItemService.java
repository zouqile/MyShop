package com.example.myshop.service;

import android.content.Context;

import com.example.myshop.callback.ListCampaignCategoryCallBack;
import com.example.myshop.callback.ListItemCategoryCallBack;
import com.example.myshop.common.Contants;
import com.example.myshop.common.ServiceUrl;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

/**
 * Created by zouqile on 2016-03-02.
 * 商品的服务
 */
public class ItemService {
    private Context context;

    public ItemService() {
    }

    public ItemService(Context context) {
        this.context = context;
    }


    //获取首页广告类型分类列表（方法返回值不能作为重载条件）
    public void CallCampaignCategoryList(ListCampaignCategoryCallBack callBack) {
        OkHttpUtils.get()
                .url(Contants.API.CAMPAIGN_HOME)
                        //.url(ServiceUrl.HOME_COMPAIGIN)
                .build()
                .execute(callBack);

    }

    //获取类型的分类列表
    public void CallItemCategoryList(ListItemCategoryCallBack callBack) {
        OkHttpUtils.get()
                .url(Contants.API.CATEGORY_LIST)
                .build()
                .execute(callBack);
    }
}
