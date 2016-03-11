package com.example.myshop.fragment;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.myshop.R;

/**
 * 发现
 */
public class FindFragment extends Fragment implements View.OnClickListener {

    private ViewPager viewPager;
    private FindPagerAdapter adapter;
    private View hideView;

    public FindFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        hideView = view.findViewById(R.id.find_hide_layout);
        viewPager = (ViewPager) view.findViewById(R.id.find_viewpager);
        adapter = new FindPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        view.findViewById(R.id.find_rb1).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_rb1:
                FindContentFragment findContentFragment = (FindContentFragment) adapter.getItem(1);
                findContentFragment.setCagegory(1);
                break;
        }
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
