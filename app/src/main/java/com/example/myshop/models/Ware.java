package com.example.myshop.models;

import java.io.Serializable;

/**
 * Created by zouqile on 2016-03-06.
 * 单个商品
 */
public class Ware implements Serializable {

    private Long id;
    private String name;
    private String imgUrl;
    private String description;
    private Float price;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
