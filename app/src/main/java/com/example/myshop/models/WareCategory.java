package com.example.myshop.models;

import java.io.Serializable;

/**
 * Created by zouqile on 2016-03-02.
 * 商品的分类（图书，家用电器等）
 */
public class WareCategory implements Serializable {

    public WareCategory() {
    }

    public WareCategory(String name) {
        this.name = name;
    }

    public WareCategory(int id, String name) {
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
