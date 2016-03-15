package com.example.myshop.models.bmob;

import com.example.myshop.models.CampaignCard;

import cn.bmob.v3.BmobObject;

/**
 * Created by zouqile on 2016-03-14.
 */
public class BmobCampaignCategory extends BmobObject {
    public BmobCampaignCategory(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public BmobCampaignCategory() {
    }

    ;

    private int id;
    private String title;

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
