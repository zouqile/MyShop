package com.example.myshop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myshop.R;
import com.example.myshop.activity.LoginActivity;

/**
 * 我的
 */
public class MineFragment extends Fragment implements View.OnClickListener {


    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        //((View) view.findViewById(R.id.mine_item_view_shop_cart)).setOnClickListener(this);//购物车(拿不到)
        ((View) view.findViewById(R.id.mime_head_img)).setOnClickListener(this);//头像
        ((View) view.findViewById(R.id.mine_tv_loginorreg)).setOnClickListener(this);//注册|登录
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_item_view_shop_cart:
                Toast.makeText(getActivity(), "购物车", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mime_head_img:
            case R.id.mine_tv_loginorreg:
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
