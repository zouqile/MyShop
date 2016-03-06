package com.example.myshop.service;

import android.content.Context;

import com.example.myshop.models.Ware;
import com.example.myshop.models.WareCart;
import com.example.myshop.util.CartPrefHelper;

import java.util.List;

/**
 * Created by zouqile on 2016-03-06.
 * 购物车的服务
 */
public class CartService {
    private CartPrefHelper prefHelper;
    private Context context;

    public CartService(Context context) {
        this.context = context;
        prefHelper = new CartPrefHelper(context);
    }


    public void put(Ware ware) {
        prefHelper.put(ware);
    }

    public void update(WareCart cart) {

        prefHelper.update(cart);
    }

    public void delete(WareCart cart) {
        prefHelper.delete(cart);
    }

    public List<WareCart> getAll() {
        return prefHelper.getAll();
    }
}
