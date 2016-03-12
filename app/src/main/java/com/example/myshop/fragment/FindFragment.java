package com.example.myshop.fragment;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.myshop.R;

/**
 * 发现
 */
public class FindFragment extends Fragment implements View.OnClickListener {

    private ViewPager viewPager;
    private FindPagerAdapter adapter;
    private RadioGroup radioGroup;
    private HorizontalScrollView scrollView;
    private int screenHalf = 0;
    private View fragment_find_view;

    public FindFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        fragment_find_view = view;
        scrollView = (HorizontalScrollView) view.findViewById(R.id.find_hscrollview);
        radioGroup = (RadioGroup) view.findViewById(R.id.find_rg);
        viewPager = (ViewPager) view.findViewById(R.id.find_viewpager);
        adapter = new FindPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        view.findViewById(R.id.find_rb1).setOnClickListener(this);
        initEvent();
        screenHalf = getScreenHalf();
        return view;
    }


    private FindContentFragment findContentFragment;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_rb1:
                findContentFragment = (FindContentFragment) adapter.getItem(1);
                findContentFragment.setCagegory(1);
                break;
        }
    }

    private int getScreenHalf() {
        Display d = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        d.getMetrics(dm);
        return d.getWidth() / 2;//屏幕宽度的一半
    }


    private void initEvent() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int scrollX = scrollView.getScrollX();//当前scrollView的位置
                System.out.println("scrollX----->" + scrollX);
                RadioButton rb = (RadioButton) fragment_find_view.findViewById(checkedId);
                System.out.println("checkedId----->" + checkedId);
                int left = rb.getLeft();//相对父控件的左边位置
                System.out.println("left----->" + left);
                int leftScreen = left - scrollX;
                scrollView.smoothScrollBy((leftScreen - screenHalf + rb.getWidth() / 2), 0);
            }
        });
    }


    static class FindPagerAdapter extends FragmentPagerAdapter {

        private FindContentFragment findContentFragment;

        public FindPagerAdapter(FragmentManager fm) {
            super(fm);
            findContentFragment = new FindContentFragment();
        }

        @Override
        public Fragment getItem(int position) {
            return findContentFragment;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
