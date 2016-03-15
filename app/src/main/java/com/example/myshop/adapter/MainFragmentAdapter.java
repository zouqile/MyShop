package com.example.myshop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.myshop.fragment.CartFragment;
import com.example.myshop.fragment.CategoryFragment;
import com.example.myshop.fragment.FindFragment;
import com.example.myshop.fragment.HomeFragment;
import com.example.myshop.fragment.MineFragment;

/**
 * Created by zouqile on 2016-02-29.
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {
    public static final int TAB_COUNT = 5;
    //
    public static final int TAB_HOME = 0x0;
    public static final int TAB_CATEGORY = 0x1;
    public static final int TAB_FIND = 0x2;
    public static final int TAB_CART = 0x3;
    public static final int TAB_MINE = 0x4;

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TAB_HOME:
                return HomeFragment.GetInstance();
            case TAB_CATEGORY:
                return CategoryFragment.GetInstance();
            case TAB_FIND:
                return FindFragment.GetInstance();
            case TAB_CART:
                return CartFragment.GetInstance();
            case TAB_MINE:
                return MineFragment.GetInstance();
        }
        return new HomeFragment();
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
