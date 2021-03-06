package com.example.myshop.models;

import java.io.Serializable;

/**
 * Created by zouqile on 2016-03-06.
 * 单个商品
 */
public class Ware implements Serializable {

    private int id;
    private String name;
    private String imgUrl;
    private String description;
    private Float price;
    private int categoryId;

    public Ware() {
    }

    public Ware(int id, String name, String imgUrl, String description, Float price, int categoryId) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

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

    @Override
    public String toString() {
        return "Ware{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
