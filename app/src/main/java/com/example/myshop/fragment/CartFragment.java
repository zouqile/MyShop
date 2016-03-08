package com.example.myshop.fragment;


import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.myshop.R;
import com.example.myshop.adapter.CartAdapter;
import com.example.myshop.common.DividerItemDecoration;
import com.example.myshop.models.WareCart;
import com.example.myshop.service.CartService;

import java.util.List;

/**
 * 购物车
 */
public class CartFragment extends Fragment {

    private CartService cartService;

    private RecyclerView cartlistview;
    private CartAdapter cartAdapter;
    private TextView total_tv;
    private CheckBox checkbox_all;
    private TextView tv_operate_change;
    private Button btnOrder;

    private static final int OPERATE_BUY = 0x1;
    private static final int OPERATE_DEL = 0x2;
    private int operateType = OPERATE_BUY;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartlistview = (RecyclerView) view.findViewById(R.id.cart_recyclerview);
        total_tv = (TextView) view.findViewById(R.id.cart_tv_total);
        checkbox_all = (CheckBox) view.findViewById(R.id.cart_checkbox_all);
        tv_operate_change = (TextView) view.findViewById(R.id.cart_tv_operate_change);
        btnOrder = (Button) view.findViewById(R.id.cart_btn_order);
        init();
        addEvent();
        initData();
        return view;
    }

    private void init() {
        cartService = new CartService(getActivity());
        cartAdapter = new CartAdapter(getActivity());
        //cartlistview.setNestedScrollingEnabled(false);
        cartlistview.setHasFixedSize(false);
        cartlistview.setLayoutManager(new LinearLayoutManager(getContext()));
        cartlistview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        cartlistview.setAdapter(cartAdapter);
    }

    private void initData() {
        cartAdapter.setCarts(cartService.getAll());
    }

    private void changeOperate(int operate) {
        this.operateType = operate;
        if (this.operateType == OPERATE_BUY) {
            tv_operate_change.setText(getResources().getString(R.string.cart_operate_edit));
            btnOrder.setText(getResources().getString(R.string.cart_operate_buy));
            total_tv.setVisibility(View.VISIBLE);
            cartAdapter.checkAllChange(true);
            checkbox_all.setChecked(true);

        } else if (this.operateType == OPERATE_DEL) {
            tv_operate_change.setText(getResources().getString(R.string.cart_operate_edit_complete));
            btnOrder.setText(getResources().getString(R.string.cart_operate_del));
            total_tv.setVisibility(View.GONE);
            cartAdapter.checkAllChange(false);
            checkbox_all.setChecked(false);
        }
    }


    private void addEvent() {
        cartAdapter.setPriceListener(new CartAdapter.PriceListener() {
            @Override
            public void priceChange(float price) {
                price = (float) (Math.round(price * 10)) / 10;
                CartFragment.this.total_tv.setText("合计：¥" + price);
            }
        });


        checkbox_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartFragment.this.cartAdapter.checkAllChange(CartFragment.this.checkbox_all.isChecked());
            }
        });


        tv_operate_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartFragment.this.operateType = CartFragment.this.operateType == CartFragment.OPERATE_BUY ? CartFragment.OPERATE_DEL : CartFragment.OPERATE_BUY;
                CartFragment.this.changeOperate(CartFragment.this.operateType);
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartFragment.this.operateType == CartFragment.OPERATE_BUY) {//买东西

                } else {//删除商品
                    CartFragment.this.cartAdapter.deleteSelete();
                }
            }
        });
        cartAdapter.setCheckAllListener(new CartAdapter.checkAllListener() {
            @Override
            public void checkAll(boolean check) {
                CartFragment.this.checkbox_all.setChecked(check);
            }
        });
    }
}
