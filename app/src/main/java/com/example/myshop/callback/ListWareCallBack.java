package com.example.myshop.callback;

import com.example.myshop.models.Ware;
import com.example.myshop.models.WareCategory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by zouqile on 2016-03-06.
 */
public abstract class ListWareCallBack extends Callback<List<Ware>> {
    @Override
    public List<Ware> parseNetworkResponse(Response response) throws Exception {
        String json = response.body().string();
        String jsonObject = new JSONObject(json).getString("list");
        List<Ware> list = new Gson().fromJson(jsonObject.toString(), new TypeToken<List<Ware>>() {
        }.getType());
        return list;
    }
}
