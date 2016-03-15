package com.example.myshop.callback;

import com.example.myshop.models.CampaignCategory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zouqile on 2016-03-02.
 */
public abstract class ListCampaignCategoryCallBack extends Callback<List<CampaignCategory>> {
    @Override
    public List<CampaignCategory> parseNetworkResponse(Response response) throws Exception {
        String string = response.body().string();
        List<CampaignCategory> list = new Gson().fromJson(string, new TypeToken<List<CampaignCategory>>() {
        }.getType());
        return list;
    }

}
