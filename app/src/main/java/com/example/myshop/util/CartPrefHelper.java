package com.example.myshop.util;

import android.content.Context;
import android.util.SparseArray;

import com.example.myshop.models.Ware;
import com.example.myshop.models.WareCart;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouqile on 2016-03-06.
 */
public class CartPrefHelper {
    private static final String CART_JSON = "cart_json";

    private SparseArray<WareCart> datas = null;

    private Context mContext;


    public CartPrefHelper(Context context) {

        mContext = context;
        datas = new SparseArray<>(10);
        listToSparse();

    }


    private void put(WareCart cart) {
        WareCart temp = datas.get(cart.getId());
        if (temp != null) {
            temp.setCount(temp.getCount() + 1);
        } else {
            temp = cart;
            temp.setCount(1);
        }
        datas.put(cart.getId(), temp);
        commit();
    }


    public void put(Ware ware) {
        WareCart cart = convertData(ware);
        put(cart);
    }

    public void update(WareCart cart) {

        datas.put(cart.getId(), cart);
        commit();
    }

    public void delete(WareCart cart) {
        datas.delete(cart.getId());
        commit();
    }

    public List<WareCart> getAll() {

        return getDataFromLocal();
    }


    private void commit() {
        List<WareCart> carts = sparseToList();
        PreferencesUtils.putString(mContext, CART_JSON, JSONUtil.toJSON(carts));
    }

    private List<WareCart> sparseToList() {


        int size = datas.size();

        List<WareCart> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {

            list.add(datas.valueAt(i));
        }
        return list;

    }


    private void listToSparse() {

        List<WareCart> carts = getDataFromLocal();

        if (carts != null && carts.size() > 0) {

            for (WareCart cart :
                    carts) {

                datas.put(cart.getId(), cart);
            }
        }

    }


    private List<WareCart> getDataFromLocal() {

        String json = PreferencesUtils.getString(mContext, CART_JSON);
        List<WareCart> carts = null;
        if (json != null) {

            carts = JSONUtil.fromJson(json, new TypeToken<List<WareCart>>() {
            }.getType());

        }
        return carts;
    }

    private WareCart convertData(Ware item) {
        WareCart cart = new WareCart();
        cart.setId(item.getId());
        cart.setDescription(item.getDescription());
        cart.setImgUrl(item.getImgUrl());
        cart.setName(item.getName());
        cart.setPrice(item.getPrice());
        return cart;
    }
}
