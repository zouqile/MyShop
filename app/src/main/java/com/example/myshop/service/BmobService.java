package com.example.myshop.service;


import android.content.Context;

import com.example.myshop.callback.BmobCallback;
import com.example.myshop.models.CampaignCard;
import com.example.myshop.models.CampaignCategory;
import com.example.myshop.models.Ware;
import com.example.myshop.models.WareCategory;
import com.example.myshop.models.bmob.BmobCampaignCard;
import com.example.myshop.models.bmob.BmobCampaignCategory;
import com.example.myshop.models.bmob.BmobWare;
import com.example.myshop.models.bmob.BmobWareCategory;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by zouqile on 2016-03-14.
 */
public class BmobService {
    private Context context;

    public BmobService(Context context) {
        this.context = context;
    }

    private void addCampaignCategoryList(List<CampaignCategory> list, final BmobCallback callback) {
        final List<BmobObject> cateList = new ArrayList<>();
        final List<BmobObject> cardList = new ArrayList<>();

        for (CampaignCategory c : list) {
            BmobCampaignCategory bmob = new BmobCampaignCategory(c.getId(), c.getTitle());
            BmobCampaignCard card1 = new BmobCampaignCard();
            card1.setId(c.getCpOne().getId());
            card1.setType("CpOne");
            card1.setTitle(c.getCpOne().getTitle());
            card1.setCid(c.getId());
            card1.setImgUrl(c.getCpOne().getImgUrl());


            BmobCampaignCard card2 = new BmobCampaignCard();
            card2.setId(c.getCpTwo().getId());
            card2.setType("CpTwo");
            card2.setTitle(c.getCpTwo().getTitle());
            card2.setCid(c.getId());
            card2.setImgUrl(c.getCpTwo().getImgUrl());


            BmobCampaignCard card3 = new BmobCampaignCard();
            card3.setId(c.getCpThree().getId());
            card3.setType("CpThree");
            card3.setTitle(c.getCpThree().getTitle());
            card3.setCid(c.getId());
            card3.setImgUrl(c.getCpThree().getImgUrl());
            cardList.add(card1);
            cardList.add(card2);
            cardList.add(card3);

            cateList.add(bmob);
        }

        insertBatch(cateList, new BmobCallback<Boolean>() {
            @Override
            public void result(Boolean result, boolean b, String msg) {
                if (result) {
                    insertBatch(cardList, callback);
                }
            }
        });
    }

    private void addWareCategoryList(List<WareCategory> wlist, final BmobCallback callback) {
        List<BmobObject> inserList = new ArrayList<>();
        for (WareCategory ware : wlist) {
            BmobWareCategory bware = new BmobWareCategory(ware.getId(), ware.getName());
            inserList.add(bware);
        }
        insertBatch(inserList, callback);
    }


    private void updateWareList() {

    }

    public void addWareList(List<Ware> wlist, final BmobCallback callback) {
        List<BmobObject> inserList = new ArrayList<>();
        for (Ware ware : wlist) {
            BmobWare bware = new BmobWare(ware.getId(), ware.getName(), ware.getImgUrl(), ware.getDescription(), ware.getPrice(), ware.getCategoryId());
            inserList.add(bware);
        }
        insertBatch(inserList, callback);
    }


    private void insertBatch(List<BmobObject> list, final BmobCallback callback) {
        new BmobObject().insertBatch(context, list, new SaveListener() {
            @Override
            public void onSuccess() {
                callback.result(true, true, "success");
            }

            @Override
            public void onFailure(int i, String s) {
                callback.result(false, false, s);
            }
        });
    }


    //这样写有错误吗？
    public void callCampaignCategoryList(final BmobCallback<List<CampaignCategory>> callback) {
        final BmobQuery<BmobCampaignCategory> query = new BmobQuery<BmobCampaignCategory>();
        final List<CampaignCategory> resultList = new ArrayList<>();
        query.findObjects(context, new FindListener<BmobCampaignCategory>() {
            @Override
            public void onSuccess(final List<BmobCampaignCategory> categorylist) {
                final BmobQuery<BmobCampaignCard> queryCard = new BmobQuery<BmobCampaignCard>();
                queryCard.findObjects(context, new FindListener<BmobCampaignCard>() {
                    @Override
                    public void onSuccess(List<BmobCampaignCard> cardlist) {
                        List<CampaignCategory> result = new ArrayList<CampaignCategory>();
                        HashMap<Integer, CampaignCategory> cMap = new HashMap<Integer, CampaignCategory>();
                        for (BmobCampaignCategory bc : categorylist) {
                            CampaignCategory c = new CampaignCategory(bc.getId(), bc.getTitle());
                            result.add(c);
                            cMap.put(c.getId(), c);
                        }

                        for (BmobCampaignCard card : cardlist) {
                            CampaignCategory c = cMap.get(card.getCid());
                            if (null == c) {
                                continue;
                            }
                            if (card.getType().equals("CpOne")) {
                                c.setCpOne(new CampaignCard(card.getId(), card.getTitle(), card.getImgUrl()));
                            } else if (card.getType().equals("CpTwo")) {
                                c.setCpTwo(new CampaignCard(card.getId(), card.getTitle(), card.getImgUrl()));
                            } else if (card.getType().equals("CpThree")) {
                                c.setCpThree(new CampaignCard(card.getId(), card.getTitle(), card.getImgUrl()));
                            }
                        }
                        callback.result(result, true, "success");
                    }

                    @Override
                    public void onError(int i, String s) {
                        callback.result(null, false, s);
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                callback.result(null, false, s);
            }
        });
    }


    public void callWareCategoryList(final BmobCallback<List<WareCategory>> callback) {
        final BmobQuery<BmobWareCategory> query = new BmobQuery<BmobWareCategory>();
        query.findObjects(context, new FindListener<BmobWareCategory>() {
            @Override
            public void onSuccess(List<BmobWareCategory> list) {
                List<WareCategory> retrunList = new ArrayList<WareCategory>();
                for (BmobWareCategory c : list) {
                    retrunList.add(new WareCategory(c.getId(), c.getName()));
                }
                callback.result(retrunList, true, "success");
            }

            @Override
            public void onError(int i, String s) {
                callback.result(null, false, s);
            }
        });
    }

    public void callWareList(final BmobCallback<List<Ware>> callback, int categoryId, int currPage, int pageSize) {
        BmobQuery<BmobWare> query = new BmobQuery<BmobWare>();
        query.addWhereEqualTo("categoryId", categoryId);
        query.setLimit(pageSize);// 限制最多pageSize条数据结果作为一页
        query.setSkip((currPage - 1) * pageSize); // 忽略前???条数据（即第一页数据结果）
        query.findObjects(context, new FindListener<BmobWare>() {
            @Override
            public void onSuccess(List<BmobWare> list) {
                List<Ware> returnList = new ArrayList<Ware>();
                for (BmobWare b : list) {
                    returnList.add(new Ware(b.getId(), b.getName(), b.getImgUrl(), b.getDescription(), b.getPrice(), b.getCategoryId()));
                }
                callback.result(returnList, true, "success");
            }

            @Override
            public void onError(int i, String s) {
                callback.result(null, false, s);
            }
        });

    }

}
