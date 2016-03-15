package com.example.myshop.service;

import android.content.Context;

import com.example.myshop.callback.BmobCallback;
import com.example.myshop.callback.ListCampaignCategoryCallBack;
import com.example.myshop.callback.ListWareCallBack;
import com.example.myshop.callback.ListWareCategoryCallBack;
import com.example.myshop.common.Contants;
import com.example.myshop.models.CampaignCategory;
import com.example.myshop.models.Ware;
import com.example.myshop.models.WareCategory;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

/**
 * Created by zouqile on 2016-03-02.
 * 商品的服务
 */
public class WareService {
    private Context context;
    private BmobService bmobService;

    public WareService() {
    }

    public WareService(Context context) {
        this.context = context;
        bmobService = new BmobService(this.context);
    }


    //获取首页广告类型分类列表（方法返回值不能作为重载条件）
    public void CallCampaignCategoryList(final ListCampaignCategoryCallBack callBack) {
        //////////
        OkHttpUtils.get()
                .url(Contants.API.CAMPAIGN_HOME)
                        //.url(ServiceUrl.HOME_COMPAIGIN)
                .build()
                .execute(callBack);
        ///////////////
//        bmobService.callCampaignCategoryList(new BmobCallback<List<CampaignCategory>>() {
//            @Override
//            public void result(List<CampaignCategory> campaignCategories, boolean result, String msg) {
//                callBack.onResponse(campaignCategories);
//            }
//        });

    }

    //获取类型的分类列表
    public void CallWareCategoryList(final ListWareCategoryCallBack callBack) {
        OkHttpUtils.get()
                .url(Contants.API.CATEGORY_LIST)
                .build()
                .execute(callBack);

//        bmobService.callWareCategoryList(new BmobCallback<List<WareCategory>>() {
//            @Override
//            public void result(List<WareCategory> wareCategories, boolean result, String msg) {
//                callBack.onResponse(wareCategories);
//            }
//        });
    }

    public void CallWareList(final ListWareCallBack callBack, int categoryId, int currPage, int pageSize) {
        OkHttpUtils.get()
                .url(Contants.API.WARES_LIST + "?categoryId=" + categoryId + "&curPage=" + currPage + "&pageSize=" + pageSize)
                .build()
                .execute(callBack);

//        bmobService.callWareList(new BmobCallback<List<Ware>>() {
//            @Override
//            public void result(List<Ware> wares, boolean result, String msg) {
//                callBack.onResponse(wares);
//
//            }
//        }, categoryId, currPage, pageSize);

    }

}
