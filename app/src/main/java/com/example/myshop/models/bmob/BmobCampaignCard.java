package com.example.myshop.models.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by zouqile on 2016-03-14.
 */
public class BmobCampaignCard extends BmobObject {
    private int id;
    private String title;
    private String imgUrl;

    private int cid;
    private String type;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
