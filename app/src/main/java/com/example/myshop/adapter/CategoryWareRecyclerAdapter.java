package com.example.myshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshop.R;
import com.example.myshop.models.Ware;
import com.example.myshop.service.LoadImgService;

import java.util.List;

/**
 * Created by zouqile on 2016-03-06.
 * 分类里面的商品列表适配器
 */
public class CategoryWareRecyclerAdapter extends RecyclerView.Adapter<CategoryWareRecyclerAdapter.CatgoryViewHolder> {

    private List<Ware> wares;
    private LayoutInflater mInflater;
    private Context mContext;
    private LoadImgService imgService;
    private int pageSize = 10;
    private long category_id = 1;
    private int currPage = 1;
    private int totalPage = 2;

    public List<Ware> getWares() {
        return wares;
    }

    public void setWares(List<Ware> wares) {
        this.wares = wares;
        this.notifyDataSetChanged();
    }

    public void addWares(List<Ware> wares) {
        this.wares.addAll(wares);
        this.notifyDataSetChanged();
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public long getCategory_id() {
        return category_id;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setCategory_id(long category_id) {
        if (this.category_id != category_id) {
            this.currPage = 1;
        }
        this.category_id = category_id;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public CategoryWareRecyclerAdapter() {

    }

    public CategoryWareRecyclerAdapter(List<Ware> datas, Context context) {
        wares = datas;
        this.mContext = context;
        imgService = new LoadImgService(this.mContext);
    }

    @Override
    public CatgoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        return new CatgoryViewHolder(mInflater.inflate(R.layout.item_category_ware, parent, false));
    }

    @Override
    public void onBindViewHolder(CatgoryViewHolder holder, int position) {
        Ware ware = wares.get(position);
        holder.title.setText(ware.getName());
        holder.price.setText("￥" + ware.getPrice());
        imgService.LoadImg(holder.img, ware.getImgUrl());
    }

    @Override
    public int getItemCount() {
        return wares.size();
    }

    static class CatgoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView title;
        private TextView price;

        public CatgoryViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.category_ware_item_img);
            title = (TextView) itemView.findViewById(R.id.category_ware_item_title);
            price = (TextView) itemView.findViewById(R.id.category_ware_item_price);
        }
    }
}
