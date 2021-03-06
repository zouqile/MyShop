package com.example.myshop.models;

import java.io.Serializable;

/**
 * Created by zouqile on 2016-03-01.
 * 首页一个活动的分类
 */
public class CampaignCategory implements Serializable {
    public CampaignCategory(int id, String title) {
        this.id = id;
        this.title = title;
    }

    private int id;
    private String title;
    private CampaignCard cpOne;
    private CampaignCard cpTwo;
    private CampaignCard cpThree;


    public CampaignCard getCpOne() {
        return cpOne;
    }

    public void setCpOne(CampaignCard cpOne) {
        this.cpOne = cpOne;
    }

    public CampaignCard getCpTwo() {
        return cpTwo;
    }

    public void setCpTwo(CampaignCard cpTwo) {
        this.cpTwo = cpTwo;
    }

    public CampaignCard getCpThree() {
        return cpThree;
    }

    public void setCpThree(CampaignCard cpThree) {
        this.cpThree = cpThree;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
