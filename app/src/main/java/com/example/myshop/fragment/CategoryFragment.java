package com.example.myshop.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myshop.R;
import com.example.myshop.callback.ListItemCategoryCallBack;
import com.example.myshop.models.ItemCategory;
import com.example.myshop.service.ItemService;
import com.example.myshop.view.CategoryMenuListView;

import java.util.List;

import okhttp3.Call;

/**
 * 分类
 */
public class CategoryFragment extends Fragment {

    private CategoryMenuListView listView;
    private MenuLVAdapter adapter;
    private ItemService service;


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        listView = (CategoryMenuListView) view.findViewById(R.id.categorymenu_listview);
        service = new ItemService(getActivity());
        initMenu();
        initEvent();
        return view;
    }


    private void initEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //if (jdFragment != null) {
                //jdFragment.setData(classifys[position]);
                adapter.setIndex(position);
                // }
            }
        });
    }

    private void initMenu() {
        service.CallItemCategoryList(new ListItemCategoryCallBack() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(List<ItemCategory> response) {
                adapter = new MenuLVAdapter(getActivity(), response);
                listView.setAdapter(adapter);
            }
        });
    }

    //分类菜单的适配器
    private static class MenuLVAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private Context mContext;
        private List<ItemCategory> mDatas;
        private int mIndex = 0;

        public MenuLVAdapter(Context context, List<ItemCategory> data) {
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
            } else {
                tv_text.setTextColor(mContext.getResources().getColor(R.color.category_menu_text_default));
            }
            return convertView;
        }
    }
}
