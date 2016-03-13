package com.example.myshop.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.example.myshop.R;
import com.example.myshop.fragment.CartFragment;
import com.example.myshop.fragment.CategoryFragment;
import com.example.myshop.fragment.FindFragment;
import com.example.myshop.fragment.HomeFragment;
import com.example.myshop.fragment.MineFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * 应用FragmentTabHost的首页
 */
public class MainTabActivity extends FragmentActivity {

    private RadioGroup radioGroup;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        radioGroup = (RadioGroup) findViewById(R.id.main_rg);
        init();
        initFragment();
        initEvent();
    }

    private void init() {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheSizePercentage(20)//内存占用20%
                .diskCacheFileCount(100)//保存的数量
                .diskCacheSize(5 * 1024 * 1024)
                        //.defaultDisplayImageOptions(DisplayImageOptions.createSimple())//显示的状态,可以圆角等
                        //  .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(configuration);
    }

    private static final int CARTF_RAGMENT = 0;
    private static final int CATEGORYF_RAGMENT = 1;
    private static final int FIND_FRAGMENT = 2;
    private static final int HOMEF_RAGMENT = 3;
    private static final int MINEF_RAGMENT = 4;
    private CartFragment cartFragment;
    private CategoryFragment categoryFragment;
    private FindFragment findFragment;
    private HomeFragment homeFragment;
    private MineFragment mineFragment;

    private void initFragment() {
        cartFragment = new CartFragment();
        categoryFragment = new CategoryFragment();
        findFragment = new FindFragment();
        homeFragment = new HomeFragment();
        mineFragment = new MineFragment();
        fragments = new ArrayList<Fragment>() {
        };
        fragments.add(cartFragment);
        fragments.add(categoryFragment);
        fragments.add(findFragment);
        fragments.add(homeFragment);
        fragments.add(mineFragment);

        FragmentManager fragmentManager = this.getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.main_content_FrameLayout, cartFragment);
        transaction.add(R.id.main_content_FrameLayout, categoryFragment);
        transaction.add(R.id.main_content_FrameLayout, findFragment);
        transaction.add(R.id.main_content_FrameLayout, homeFragment);
        transaction.add(R.id.main_content_FrameLayout, mineFragment);
        transaction.show(homeFragment).hide(cartFragment).hide(categoryFragment).hide(findFragment).hide(mineFragment);
        transaction.commitAllowingStateLoss();
    }

    private void switchFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);//设置跳转动画
        for (int i = 0; i < fragments.size(); i++) {
            if (index == i) {
                transaction.show(fragments.get(index));
            } else {
                transaction.hide(fragments.get(i));
            }
        }
        transaction.commit();
        if (CARTF_RAGMENT == index) {
            cartFragment.initData();
        }
    }


    private void initEvent() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (checkedId) {
                    case R.id.main_rb_home:
                        switchFragment(HOMEF_RAGMENT);
                        // fragmentTransaction.replace(R.id.main_content_FrameLayout, homeFragment);
                        // fragmentTransaction.addToBackStack(null);
                        // fragmentTransaction.commit();
                        break;
                    case R.id.main_rb_category:
                        switchFragment(CATEGORYF_RAGMENT);
                        // fragmentTransaction.replace(R.id.main_content_FrameLayout, categoryFragment);
                        //fragmentTransaction.addToBackStack(null);
                        //fragmentTransaction.commit();
                        break;
                    case R.id.main_rb_find:
                        switchFragment(FIND_FRAGMENT);
                        // fragmentTransaction.replace(R.id.main_content_FrameLayout, findFragment);
                        //fragmentTransaction.addToBackStack(null);
                        // fragmentTransaction.commit();
                        break;
                    case R.id.main_rb_cart:
                        switchFragment(CARTF_RAGMENT);
                        //fragmentTransaction.replace(R.id.main_content_FrameLayout, cartFragment);
                        //fragmentTransaction.addToBackStack(null);
                        //fragmentTransaction.commit();
                        break;
                    case R.id.main_rb_mine:
                        switchFragment(MINEF_RAGMENT);
                        // fragmentTransaction.replace(R.id.main_content_FrameLayout, mineFragment);
                        // fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //  break;
                }
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                finish();
            } else {
                getSupportFragmentManager().popBackStack();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
