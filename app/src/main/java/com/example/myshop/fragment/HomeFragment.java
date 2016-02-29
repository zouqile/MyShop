package com.example.myshop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myshop.R;

import java.util.ArrayList;

/**
 * 首页
 */
public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private ViewPager viewpager;
    private ArrayList<ImageView> roundViews = new ArrayList<>();
    private ArrayList<ImageView> imgViews = new ArrayList<>();
    //自定义轮播图的资源ID
    private int[] imagesResIds;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewpager = (ViewPager) view.findViewById(R.id.home_viewpager);
        roundViews.add((ImageView) view.findViewById(R.id.home_slip_round_img1));
        roundViews.add((ImageView) view.findViewById(R.id.home_slip_round_img2));
        roundViews.add((ImageView) view.findViewById(R.id.home_slip_round_img3));
        roundViews.add((ImageView) view.findViewById(R.id.home_slip_round_img4));
        roundViews.add((ImageView) view.findViewById(R.id.home_slip_round_img5));
        imagesResIds = new int[]{
                R.mipmap.home_slip_ad1,
                R.mipmap.home_slip_ad2,
                R.mipmap.home_slip_ad3,
                R.mipmap.home_slip_ad4,
                R.mipmap.home_slip_ad5,
        };
        for (int imgId : imagesResIds) {
            ImageView imageView = new ImageView(getActivity());////查看是否有问题
            imageView.setImageResource(imgId);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imgViews.add(imageView);
        }
        roundViews.get(imgIndex).setImageResource(R.mipmap.main_slip_focus_bg);
        viewpager.setAdapter(new SlipPagerAdapter(imgViews));
        viewpager.addOnPageChangeListener(this);
        return view;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    private int imgIndex = 0;

    @Override
    public void onPageSelected(int position) {
        if (imgIndex != position) {
            roundViews.get(imgIndex).setImageResource(R.mipmap.main_slip_defaule_bg);
            roundViews.get(position).setImageResource(R.mipmap.main_slip_focus_bg);
            imgIndex = position;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE://0:// 滑动结束，即切换完毕或者加载完毕
                // 当前为最后一张，此时从右向左滑，则切换到第一张
                break;
            case ViewPager.SCROLL_STATE_DRAGGING://1:// 手势滑动，空闲中

                break;
            case ViewPager.SCROLL_STATE_SETTLING://2:// 界面切换中
                break;
        }

    }

    private static class SlipPagerAdapter extends PagerAdapter {

        private ArrayList<ImageView> imgViews;

        public SlipPagerAdapter(ArrayList<ImageView> imgViews) {
            this.imgViews = imgViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(imgViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(imgViews.get(position));
            return imgViews.get(position);
        }

        @Override
        public int getCount() {
            if (null == imgViews) {
                return 0;
            } else {
                return imgViews.size();
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
