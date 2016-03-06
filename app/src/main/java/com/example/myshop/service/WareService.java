package com.example.myshop.service;

import android.content.Context;

import com.example.myshop.callback.ListCampaignCategoryCallBack;
import com.example.myshop.callback.ListWareCallBack;
import com.example.myshop.callback.ListWareCategoryCallBack;
import com.example.myshop.common.Contants;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by zouqile on 2016-03-02.
 * 商品的服务
 */
public class WareService {
    private Context context;

    public WareService() {
    }

    public WareService(Context context) {
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
    public void CallWareCategoryList(ListWareCategoryCallBack callBack) {
        OkHttpUtils.get()
                .url(Contants.API.CATEGORY_LIST)
                .build()
                .execute(callBack);
    }

    public void CallWareList(ListWareCallBack callBack, long categoryId, int currPage, int pageSize) {
        OkHttpUtils.get()
                .url(Contants.API.WARES_LIST + "?categoryId=" + categoryId + "&curPage=" + currPage + "&pageSize=" + pageSize)
                .build()
                .execute(callBack);
    }
}
