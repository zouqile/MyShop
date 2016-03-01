package com.example.myshop.fragment;


import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshop.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 首页
 */
public class HomeFragment extends Fragment {

    private ViewPager viewpager;
    private ArrayList<ImageView> roundViews = new ArrayList<>();
    private ArrayList<ImageView> imgViews = new ArrayList<>();
    private AutoSlipHandler handler;
    private GridView gridviewMenu;
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
        gridviewMenu = (GridView) view.findViewById(R.id.home_menu_gridview);
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
            imageView.setImageResource(imgId);//如果是网络获取的设设置bitmap
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imgViews.add(imageView);
        }
        viewpager.setAdapter(new SlipPagerAdapter(imgViews));
        handler = new AutoSlipHandler(new WeakReference<HomeFragment>(this));
        int currentItem = (Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % 5);
        viewpager.setCurrentItem(currentItem);//默认在中间，使用户看不到边界
        imgIndex = getImgIndex(currentItem);
        roundViews.get(imgIndex).setImageResource(R.mipmap.main_slip_focus_bg);
        handler.sendMessage(handler.obtainMessage(AutoSlipHandler.MSG_PAGE_CHANGED, currentItem, 0));
        addEvent();
        //开始轮播效果
        handler.sendEmptyMessage(AutoSlipHandler.MSG_START_SLIP);
        gridviewMenu.setAdapter(new GridMenuAdapter(getActivity()));
        return view;
    }

    private void addEvent() {
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                handler.sendMessage(handler.obtainMessage(AutoSlipHandler.MSG_PAGE_CHANGED, position, 0));
                int nowImgIndex = getImgIndex(position);
                if (imgIndex != nowImgIndex) {
                    roundViews.get(imgIndex).setImageResource(R.mipmap.main_slip_defaule_bg);
                    roundViews.get(nowImgIndex).setImageResource(R.mipmap.main_slip_focus_bg);
                    imgIndex = nowImgIndex;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE://0:// 滑动结束，即切换完毕或者加载完毕
                        handler.sendEmptyMessageDelayed(AutoSlipHandler.MSG_UPDATE_IMAGE, AutoSlipHandler.TIME_DELAY);
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING://1:// 手势滑动，空闲中
                        handler.sendEmptyMessage(AutoSlipHandler.MSG_STOP_SLIP);//暂停轮播
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING://2:// 界面切换中

                        break;
                }

            }
        });

        gridviewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });
    }

    private int imgIndex = 0;

    //根据当前页面的位置计算img的位置
    private int getImgIndex(int pagePosition) {
        pagePosition %= imgViews.size();
        if (pagePosition < 0) {
            pagePosition = imgViews.size() + pagePosition;
        }
        return pagePosition;
    }


    //广告pager适配器
    private static class SlipPagerAdapter extends PagerAdapter {

        private ArrayList<ImageView> imgViews;

        public SlipPagerAdapter(ArrayList<ImageView> imgViews) {
            this.imgViews = imgViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //Warning：不要在这里调用removeView
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //对ViewPager页号求模取出View列表中要显示的项
            position %= imgViews.size();
            if (position < 0) {
                position = imgViews.size() + position;
            }
            ImageView view = imgViews.get(position);
            //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }
            container.addView(view);
            //add listeners here if necessary
            return view;
        }

        @Override
        public int getCount() {
            //设置成最大，使用户看不到边界
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    //处理自动广告
    private static class AutoSlipHandler extends Handler {

        /**
         * 请求更新显示的View。
         */
        private static final int MSG_UPDATE_IMAGE = 1;
        /**
         * 请求暂停轮播。
         */
        private static final int MSG_STOP_SLIP = 2;
        /**
         * 请求恢复轮播。
         */
        private static final int MSG_START_SLIP = 3;
        /**
         * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
         * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
         * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
         */
        private static final int MSG_PAGE_CHANGED = 4;
        //轮播间隔时间
        private static final long TIME_DELAY = 3000;
        private int currentItem = 0;

        //使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
        private WeakReference<HomeFragment> weakReference;

        protected AutoSlipHandler(WeakReference<HomeFragment> wk) {
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HomeFragment fragment = weakReference.get();
            if (fragment == null) {
                //Activity已经回收，无需再处理UI了
                return;
            }
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (fragment.handler.hasMessages(MSG_UPDATE_IMAGE)) {
                fragment.handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    fragment.viewpager.setCurrentItem(currentItem);
                    //准备下次播放
                    fragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, TIME_DELAY);
                    break;
                case MSG_STOP_SLIP:
                    //只要不发送消息就暂停了
                    break;
                case MSG_START_SLIP:
                    fragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, TIME_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem = msg.arg1;
                    break;
                default:
                    break;
            }
        }
    }

    //menu的gridview的适配器
    private static class GridMenuAdapter extends BaseAdapter {

        private Activity activity;

        public GridMenuAdapter(Activity _activity) {
            activity = _activity;
            initData();
        }

        private void initData() {
            res = new int[]{R.mipmap.home_menu_market,
                    R.mipmap.home_menu_hot,
                    R.mipmap.home_menu_vip,
                    R.mipmap.home_menu_else
            };
            Resources resources = activity.getResources();
            strs = new String[]{resources.getString(R.string.home_menu_market),
                    resources.getString(R.string.home_menu_hot),
                    resources.getString(R.string.home_menu_vip),
                    resources.getString(R.string.home_menu_else)
            };
        }

        //资源没有从网络获取
        private int[] res;
        private String[] strs;

        @Override
        public int getCount() {
            return res.length;
        }

        @Override
        public Object getItem(int position) {
            return strs[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //只有四个，没用holder
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.item_home_menu_gridview, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.home_menu_img);
            TextView textView = (TextView) view.findViewById(R.id.home_menu_tv);
            imageView.setImageResource(res[position]);
            textView.setText(strs[position]);
            return view;
        }
    }
}
