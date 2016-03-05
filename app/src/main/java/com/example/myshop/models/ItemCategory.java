package com.example.myshop.models;

import java.io.Serializable;

/**
 * Created by zouqile on 2016-03-02.
 * 商品的分类（图书，家用电器等）
 */
public class ItemCategory implements Serializable {

    public ItemCategory() {
    }

    public ItemCategory(String name) {
        this.name = name;
    }

    public ItemCategory(long id, String name) {
        this.id = id;
        this.name = name;
    }

    private long id;
    private String name;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
