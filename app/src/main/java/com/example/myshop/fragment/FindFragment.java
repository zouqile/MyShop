package com.example.myshop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private RadioButton find_rb1;
    private RadioButton find_rb2;
    private RadioButton find_rb3;
    private RadioButton find_rb4;
    private RadioButton find_rb5;
    private RadioButton find_rb6;

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
        (find_rb1 = (RadioButton) view.findViewById(R.id.find_rb1)).setOnClickListener(this);
        (find_rb2 = (RadioButton) view.findViewById(R.id.find_rb2)).setOnClickListener(this);
        (find_rb3 = (RadioButton) view.findViewById(R.id.find_rb3)).setOnClickListener(this);
        (find_rb4 = (RadioButton) view.findViewById(R.id.find_rb4)).setOnClickListener(this);
        (find_rb5 = (RadioButton) view.findViewById(R.id.find_rb5)).setOnClickListener(this);
        (find_rb6 = (RadioButton) view.findViewById(R.id.find_rb6)).setOnClickListener(this);
        adapter = new FindPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.TAB_COUNT);//缓存数据
        initEvent();
        screenHalf = getScreenHalf();
        return view;
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
                RadioButton rb = (RadioButton) fragment_find_view.findViewById(checkedId);
                int left = rb.getLeft();//相对父控件的左边位置
                int leftScreen = left - scrollX;
                scrollView.smoothScrollBy((leftScreen - screenHalf + rb.getWidth() / 2), 0);

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case FindPagerAdapter.TAB_FINDCONTENT_0:
                        radioGroup.check(R.id.find_rb1);
                        setFragmentIndex(FindPagerAdapter.TAB_FINDCONTENT_0);
                        break;
                    case FindPagerAdapter.TAB_FINDCONTENT_1:
                        radioGroup.check(R.id.find_rb2);
                        setFragmentIndex(FindPagerAdapter.TAB_FINDCONTENT_1);
                        break;
                    case FindPagerAdapter.TAB_FINDCONTENT_2:
                        radioGroup.check(R.id.find_rb3);
                        setFragmentIndex(FindPagerAdapter.TAB_FINDCONTENT_2);
                        break;
                    case FindPagerAdapter.TAB_FINDCONTENT_3:
                        radioGroup.check(R.id.find_rb4);
                        setFragmentIndex(FindPagerAdapter.TAB_FINDCONTENT_3);
                        break;
                    case FindPagerAdapter.TAB_FINDCONTENT_4:
                        radioGroup.check(R.id.find_rb5);
                        setFragmentIndex(FindPagerAdapter.TAB_FINDCONTENT_4);
                        break;
                    case FindPagerAdapter.TAB_FINDCONTENT_5:
                        radioGroup.check(R.id.find_rb6);
                        setFragmentIndex(FindPagerAdapter.TAB_FINDCONTENT_5);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setFragmentIndex(int fragmentIndex) {
        FindContentFragment findContentFragment;
        viewPager.setCurrentItem(fragmentIndex);
        findContentFragment = (FindContentFragment) adapter.getItem(fragmentIndex);
        findContentFragment.setCagegory(fragmentIndex + 1);
    }

    @Override
    public void onClick(View v) {
        FindContentFragment findContentFragment;
        switch (v.getId()) {
            case R.id.find_rb1:
                setFragmentIndex(FindPagerAdapter.TAB_FINDCONTENT_0);
                break;
            case R.id.find_rb2:
                setFragmentIndex(FindPagerAdapter.TAB_FINDCONTENT_1);
                break;
            case R.id.find_rb3:
                setFragmentIndex(FindPagerAdapter.TAB_FINDCONTENT_2);
                break;
            case R.id.find_rb4:
                setFragmentIndex(FindPagerAdapter.TAB_FINDCONTENT_3);
                break;
            case R.id.find_rb5:
                setFragmentIndex(FindPagerAdapter.TAB_FINDCONTENT_4);
                break;
            case R.id.find_rb6:
                setFragmentIndex(FindPagerAdapter.TAB_FINDCONTENT_5);
                break;

        }
    }


    static class FindPagerAdapter extends FragmentPagerAdapter {
        public static final int TAB_COUNT = 6;
        //
        public static final int TAB_FINDCONTENT_0 = 0x0;
        public static final int TAB_FINDCONTENT_1 = 0x1;
        public static final int TAB_FINDCONTENT_2 = 0x2;
        public static final int TAB_FINDCONTENT_3 = 0x3;
        public static final int TAB_FINDCONTENT_4 = 0x4;
        public static final int TAB_FINDCONTENT_5 = 0x5;

        private FindContentFragment find0;
        private FindContentFragment find1;
        private FindContentFragment find2;
        private FindContentFragment find3;
        private FindContentFragment find4;
        private FindContentFragment find5;

        public FindPagerAdapter(FragmentManager fm) {
            super(fm);
            find0 = new FindContentFragment();
            find1 = new FindContentFragment();
            find2 = new FindContentFragment();
            find3 = new FindContentFragment();
            find4 = new FindContentFragment();
            find5 = new FindContentFragment();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return find0;
                case 1:
                    return find1;
                case 2:
                    return find2;
                case 3:
                    return find3;
                case 4:
                    return find4;
                case 5:
                    return find5;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }
}
