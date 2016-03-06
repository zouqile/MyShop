package com.example.myshop.callback;

import com.example.myshop.models.WareCategory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Response;

/**
 * Created by zouqile on 2016-03-02.
 */
public abstract class ListWareCategoryCallBack extends Callback<List<WareCategory>> {
    @Override
    public List<WareCategory> parseNetworkResponse(Response response) throws Exception {
        String string = response.body().string();
        List<WareCategory> list = new Gson().fromJson(string, new TypeToken<List<WareCategory>>() {
        }.getType());
        return list;
    }
}
