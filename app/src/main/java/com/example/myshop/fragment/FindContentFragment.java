package com.example.myshop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.myshop.R;
import com.example.myshop.activity.WareDetailActivity;
import com.example.myshop.adapter.CategoryWareRecyclerAdapter;
import com.example.myshop.callback.ListWareCallBack;
import com.example.myshop.common.Contants;
import com.example.myshop.models.Ware;
import com.example.myshop.service.WareService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 发现模块里面的内容
 */
public class FindContentFragment extends Fragment {


    private RecyclerView recyclerView;
    private MaterialRefreshLayout mRefreshLaout;
    private CategoryWareRecyclerAdapter wareAdapter;//商品的适配器
    private WareService service;


    public FindContentFragment() {
        wareAdapter = new CategoryWareRecyclerAdapter(new ArrayList<Ware>(), getContext());
        service = new WareService(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_content, container, false);
        mRefreshLaout = (MaterialRefreshLayout) view.findViewById(R.id.find_content_refresh_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.find_content_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(wareAdapter);
        initEvent();
        initRefreshLayout();
        return view;
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

    public void setCagegory(int category_id) {
        wareAdapter.setCategory_id(category_id);
        wareAdapter.setCurrPage(1);
        refreshData();
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
    }


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
}
