package com.example.myshop.models;

import java.io.Serializable;

/**
 * Created by zouqile on 2016-03-06.
 * 购物车的一个商品
 */
public class WareCart extends Ware implements Serializable {

    private int count;
    private boolean isChecked = true;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

}
