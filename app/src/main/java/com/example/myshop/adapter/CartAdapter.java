package com.example.myshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshop.R;
import com.example.myshop.activity.WareDetailActivity;
import com.example.myshop.common.Contants;
import com.example.myshop.models.WareCart;
import com.example.myshop.service.CartService;
import com.example.myshop.service.LoadImgService;

import java.util.List;

/**
 * Created by zouqile on 2016-03-07.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<WareCart> carts;
    private LayoutInflater mInflater;
    private Context mContext;
    private LoadImgService imgService;
    private CartViewHolder.CartItemOnClickListener cartItemOnClickListener;
    private PriceListener priceListener;
    private checkAllListener checkAllListener;
    private CartService cartService;

    public CartAdapter(Context context, List<WareCart> carts) {
        this.mContext = context;
        this.carts = carts;
        notifyPriceChange();
        imgService = new LoadImgService(this.mContext);
        cartService = new CartService(this.mContext);
        addClickHandler();
    }

    public void setPriceListener(PriceListener priceListener) {
        this.priceListener = priceListener;
    }

    public void setCheckAllListener(checkAllListener checkAllListener) {
        this.checkAllListener = checkAllListener;
    }


    public void checkAllChange(boolean check) {
        for (WareCart cart : carts) {
            cart.setIsChecked(check);
        }
        notifyDataSetChanged();
        notifyPriceChange();
    }

    public void deleteSelete() {
        for (WareCart cart : carts) {
            if (cart.isChecked()) {
                carts.remove(cart);
                cartService.delete(cart);
            }
        }
        notifyDataSetChanged();
        notifyPriceChange();
    }

    private WareCart clickCart;

    private void checkChange() {
        if (null == checkAllListener) {
            return;
        }
        int num = 0;
        for (WareCart cart : carts) {
            if (cart.isChecked()) {
                num++;
            }
        }
        if (num == carts.size()) {
            checkAllListener.checkAll(true);
        } else {
            checkAllListener.checkAll(false);
        }
    }

    private void addClickHandler() {
        cartItemOnClickListener = new CartViewHolder.CartItemOnClickListener() {
            @Override
            public void onClick(View view, CartViewHolder holder, int position) {
                clickCart = carts.get(position);
                switch (view.getId()) {
                    case R.id.cart_item_checkbox://check
                        if (holder.checkBox.isChecked()) {
                            clickCart.setIsChecked(true);
                        } else {
                            clickCart.setIsChecked(false);
                        }
                        checkChange();
                        CartAdapter.this.notifyItemChanged(position);
                        notifyPriceChange();
                        break;
                    case R.id.cart_item_img://点击图片，打开详情
                        Intent intent = new Intent(mContext, WareDetailActivity.class);
                        intent.putExtra(Contants.WARE, clickCart);
                        mContext.startActivity(intent);
                        break;
                    case R.id.cart_item_title_tv:
                        break;
                    case R.id.cart_item_price_tv:
                        break;
                    case R.id.cart_item_minus_img://减少
                        if (clickCart.getCount() > 1) {
                            clickCart.setCount(clickCart.getCount() - 1);
                            CartAdapter.this.cartService.update(clickCart);
                            CartAdapter.this.notifyItemChanged(position);
                            notifyPriceChange();
                        }

                        break;
                    case R.id.cart_item_count_tv:
                        break;
                    case R.id.cart_item_plus_img://增加
                        clickCart.setCount(clickCart.getCount() + 1);
                        CartAdapter.this.cartService.update(clickCart);
                        CartAdapter.this.notifyItemChanged(position);
                        notifyPriceChange();
                        break;
                }
            }
        };
    }

    private void notifyPriceChange() {
        if (null != priceListener) {
            priceListener.priceChange(getTotalPrice());
        }
    }

    private float getTotalPrice() {
        float sum = 0;
        if (null == carts || carts.size() == 0) {
            return sum;
        }

        for (WareCart cart : carts) {
            if (cart.isChecked()) {
                sum += cart.getCount() * cart.getPrice();
            }
        }
        return sum;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_cart, parent, false);
        CartViewHolder holder = new CartViewHolder(view);
        holder.setItemOnClickListener(cartItemOnClickListener);
        return holder;
    }


    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        WareCart cart = carts.get(position);
        holder.checkBox.setChecked(cart.isChecked());
        imgService.LoadImg(holder.img, cart.getImgUrl());
        holder.title_tv.setText(cart.getName());
        holder.price_tv.setText("¥" + cart.getPrice().toString());
        holder.count_tv.setText(Integer.toString(cart.getCount()));
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public static interface PriceListener {
        void priceChange(float price);
    }

    public static interface checkAllListener {
        void checkAll(boolean check);
    }

    static class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CheckBox checkBox;
        private ImageView img;
        private TextView title_tv;
        private TextView price_tv;
        private ImageView minus_img;
        private TextView count_tv;
        private ImageView plus_img;
        private CartItemOnClickListener itemOnClickListener;

        public void setItemOnClickListener(CartItemOnClickListener itemOnClickListener) {
            this.itemOnClickListener = itemOnClickListener;
        }

        public CartViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.cart_item_checkbox);
            checkBox.setOnClickListener(this);
            img = (ImageView) itemView.findViewById(R.id.cart_item_img);
            img.setOnClickListener(this);
            title_tv = (TextView) itemView.findViewById(R.id.cart_item_title_tv);
            title_tv.setOnClickListener(this);
            price_tv = (TextView) itemView.findViewById(R.id.cart_item_price_tv);
            price_tv.setOnClickListener(this);
            minus_img = (ImageView) itemView.findViewById(R.id.cart_item_minus_img);
            minus_img.setOnClickListener(this);
            count_tv = (TextView) itemView.findViewById(R.id.cart_item_count_tv);
            count_tv.setOnClickListener(this);
            plus_img = (ImageView) itemView.findViewById(R.id.cart_item_plus_img);
            plus_img.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != itemOnClickListener) {
                itemOnClickListener.onClick(v, this, getLayoutPosition());
            }

        }

        static interface CartItemOnClickListener {
            void onClick(View view, CartViewHolder holder, int position);
        }
    }
}
