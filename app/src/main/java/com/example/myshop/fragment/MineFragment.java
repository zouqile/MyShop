package com.example.myshop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myshop.R;

/**
 * 我的
 */
public class MineFragment extends Fragment {


    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    

    //在界面直接注册了事件了
    public void mineItemViewClick(View view) {
        switch (view.getId()) {
            case R.id.MineItemView_shop_cart:
                Toast.makeText(getActivity(), "购物车", Toast.LENGTH_SHORT).show();
                break;
        }

    }

}
