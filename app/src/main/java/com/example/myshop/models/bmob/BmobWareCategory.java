package com.example.myshop.models.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by zouqile on 2016-03-14.
 */
public class BmobWareCategory extends BmobObject {
    public BmobWareCategory() {
    }

    public BmobWareCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
