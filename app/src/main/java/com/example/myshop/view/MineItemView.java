package com.example.myshop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myshop.R;

/**
 * Created by zouqile on 2016-03-08.
 * 我的里面一个功能的item
 */
public class MineItemView extends RelativeLayout {
    private Context context;
    private int imgSrc;
    private String title;


    public MineItemView(Context context, AttributeSet attrs) {
        super(context);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MineItemView);
        imgSrc = array.getResourceId(R.styleable.MineItemView_mimeItemImgSrc, R.mipmap.me_my_order);
        title = array.getString(R.styleable.MineItemView_mimeItemTitle);
        inflate(context, R.layout.layout_mine_item, this);
        array.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        TextView textView = (TextView) findViewById(R.id.mine_item_textView);
        textView.setText(title);
        ImageView imageView = (ImageView) findViewById(R.id.mine_item_imageView);
        imageView.setImageResource(imgSrc);
    }
}
