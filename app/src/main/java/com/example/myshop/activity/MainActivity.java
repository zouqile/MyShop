package com.example.myshop.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.myshop.R;
import com.example.myshop.adapter.MainFragmentAdapter;

public class MainActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private RadioButton rb_home, rb_category, rb_find,
            rb_cart, rb_mine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        viewPager.setOffscreenPageLimit(MainFragmentAdapter.TAB_COUNT);//缓存数据
        rb_home = (RadioButton) findViewById(R.id.main_rb_home);
        rb_category = (RadioButton) findViewById(R.id.main_rb_category);
        rb_find = (RadioButton) findViewById(R.id.main_rb_find);
        rb_cart = (RadioButton) findViewById(R.id.main_rb_cart);
        rb_mine = (RadioButton) findViewById(R.id.main_rb_mine);
        rb_home.setOnClickListener(this);
        rb_cart.setOnClickListener(this);
        rb_category.setOnClickListener(this);
        rb_find.setOnClickListener(this);
        rb_mine.setOnClickListener(this);

        MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        rb_home.setChecked(true);//开始是显示首页
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_rb_home:
                viewPager.setCurrentItem(MainFragmentAdapter.TAB_HOME);
                break;
            case R.id.main_rb_category:
                viewPager.setCurrentItem(MainFragmentAdapter.TAB_CATEGORY);
                break;
            case R.id.main_rb_find:
                viewPager.setCurrentItem(MainFragmentAdapter.TAB_FIND);
                break;
            case R.id.main_rb_cart:
                viewPager.setCurrentItem(MainFragmentAdapter.TAB_CART);
                break;
            case R.id.main_rb_mine:
                viewPager.setCurrentItem(MainFragmentAdapter.TAB_MINE);
                break;
            default:
                break;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case MainFragmentAdapter.TAB_CART:
                rb_cart.setChecked(true);
                break;
            case MainFragmentAdapter.TAB_CATEGORY:
                rb_category.setChecked(true);
                break;
            case MainFragmentAdapter.TAB_FIND:
                rb_find.setChecked(true);
                break;
            case MainFragmentAdapter.TAB_HOME:
                rb_home.setChecked(true);
                break;
            case MainFragmentAdapter.TAB_MINE:
                rb_mine.setChecked(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
