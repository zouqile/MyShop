package com.example.myshop.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.myshop.R;
import com.example.myshop.activity.WareDetailActivity;
import com.example.myshop.adapter.CategoryWareRecyclerAdapter;
import com.example.myshop.callback.ListWareCallBack;
import com.example.myshop.callback.ListWareCategoryCallBack;
import com.example.myshop.common.Contants;
import com.example.myshop.models.Ware;
import com.example.myshop.models.WareCategory;
import com.example.myshop.service.WareService;
import com.example.myshop.view.CategoryMenuListView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 分类
 */
public class CategoryFragment extends Fragment {

    private CategoryMenuListView listView;//左侧的菜单
    private MenuLVAdapter adapter;//菜单的适配器
    private WareService service;
    private MaterialRefreshLayout mRefreshLaout;
    private RecyclerView recyclerview;//右侧的商品
    private CategoryWareRecyclerAdapter wareAdapter;//商品的适配器

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        listView = (CategoryMenuListView) view.findViewById(R.id.categorymenu_listview);
        adapter = new MenuLVAdapter(getActivity(), new ArrayList<WareCategory>());
        service = new WareService(getActivity());
        wareAdapter = new CategoryWareRecyclerAdapter(new ArrayList<Ware>(), this.getActivity());
        mRefreshLaout = (MaterialRefreshLayout) view.findViewById(R.id.category_refresh_layout);
        recyclerview = (RecyclerView) view.findViewById(R.id.category_recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerview.setAdapter(wareAdapter);
        listView.setAdapter(adapter);
        // wareAdapter=new CategoryWareRecyclerAdapter(this);
        initEvent();
        initMenu();
        initRefreshLayout();
        refreshData();//第一次加载商品数据
        return view;
    }

    private void initEvent() {
        wareAdapter.setOnWareClickListener(new CategoryWareRecyclerAdapter.OnWareClickListener() {
            @Override
            public void onClick(View view, int position) {
                Ware ware = wareAdapter.getItem(position);
                Intent intent = new Intent(getActivity().getApplicationContext(), WareDetailActivity.class);
                intent.putExtra(Contants.WARE, ware);
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                WareCategory category = (WareCategory) adapter.getItem(position);
                wareAdapter.setCategory_id(category.getId());
                wareAdapter.setCurrPage(1);
                refreshData();
                adapter.setIndex(position);
            }
        });

    }

    private void initMenu() {
        service.CallWareCategoryList(new ListWareCategoryCallBack() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(List<WareCategory> response) {
                adapter.setmDatas(response);
            }
        });
    }


    //初始化加载更多的事件
    private void initRefreshLayout() {
        mRefreshLaout.setLoadMore(true);
        mRefreshLaout.setMaterialRefreshListener(new MaterialRefreshListener() {
            //下拉
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                // refreshData();
                mRefreshLaout.finishRefresh();
            }

            //上拉
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (wareAdapter.getCurrPage() <= wareAdapter.getTotalPage())
                    loadMoreData();
                else {
                    //Toast.makeText(getActivity(), "无更多数据", Toast.LENGTH_SHORT).show();
                    mRefreshLaout.finishRefreshLoadMore();
                }
            }
        });
    }

    //
    private void refreshData() {
        service.CallWareList(new ListWareCallBack() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(List<Ware> response) {
                wareAdapter.setWares(response);

            }
        }, wareAdapter.getCategory_id(), wareAdapter.getCurrPage(), wareAdapter.getPageSize());
    }

    //加载更多数据
    private void loadMoreData() {
        wareAdapter.setCurrPage(wareAdapter.getCurrPage() + 1);
        service.CallWareList(new ListWareCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                mRefreshLaout.finishRefreshLoadMore();
            }

            @Override
            public void onResponse(List<Ware> response) {
                wareAdapter.addWares(response);
                mRefreshLaout.finishRefreshLoadMore();
            }
        }, wareAdapter.getCategory_id(), wareAdapter.getCurrPage(), wareAdapter.getPageSize());
    }


    //分类菜单的适配器
    static class MenuLVAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private Context mContext;
        private List<WareCategory> mDatas;
        private int mIndex = 0;

        public List<WareCategory> getmDatas() {
            return mDatas;
        }

        public void setmDatas(List<WareCategory> mDatas) {
            this.mDatas = mDatas;
            this.notifyDataSetChanged();
        }

        public MenuLVAdapter(Context context, List<WareCategory> data) {
            this.mContext = context;
            mLayoutInflater = LayoutInflater.from(mContext);
            this.mDatas = data;
        }

        public void setIndex(int mIndex) {
            this.mIndex = mIndex;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = mLayoutInflater.inflate(R.layout.item_category_menu, null);
            TextView tv_text = (TextView) convertView.findViewById(R.id.category_menu_tv);
            tv_text.setText(mDatas.get(position).getName());
            if (mIndex == position) {
                tv_text.setTextColor(mContext.getResources().getColor(R.color.category_menu_text_sel));
                convertView.setBackgroundColor(mContext.getResources().getColor(R.color.category_menu_sel_bg));
            } else {
                tv_text.setTextColor(mContext.getResources().getColor(R.color.category_menu_text_default));
                convertView.setBackgroundColor(mContext.getResources().getColor(R.color.category_menu_default_bg));
            }
            return convertView;
        }
    }
}
