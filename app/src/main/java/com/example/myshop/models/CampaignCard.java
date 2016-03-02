package com.example.myshop.models;

import java.io.Serializable;

/**
 * Created by zouqile on 2016-03-01.
 * 一个活动首页卡片的信息
 */
public class CampaignCard implements Serializable {
    private Long id;
    private String title;
    private String imgUrl;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
