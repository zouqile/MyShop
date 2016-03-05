package com.example.myshop.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshop.R;
import com.example.myshop.models.CampaignCard;
import com.example.myshop.models.CampaignCategory;
import com.example.myshop.service.LoadImgService;

import java.util.List;

/**
 * Created by zouqile on 2016-03-01.
 */
public class HomeCatgoryRecyclerAdapter extends RecyclerView.Adapter<HomeCatgoryRecyclerAdapter.CatgoryViewHolder> {


    private static final int VIEW_TYPE_L = 0;
    private static final int VIEW_TYPE_R = 1;
    private OnCampaignCardClickListener mListener;
    private List<CampaignCategory> mDatas;
    private LayoutInflater mInflater;
    private Context mContext;
    private LoadImgService imgService;


    public HomeCatgoryRecyclerAdapter() {
    }

    public HomeCatgoryRecyclerAdapter(List<CampaignCategory> datas, Context context) {
        mDatas = datas;
        this.mContext = context;
        imgService=new LoadImgService(this.mContext);
    }

    public void setOnCampaignClickListener(OnCampaignCardClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public CatgoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_R) {
            return new CatgoryViewHolder(mInflater.inflate(R.layout.item_home_cardview_right, parent, false), this);
        }
        return new CatgoryViewHolder(mInflater.inflate(R.layout.item_home_cardview_left, parent, false), this);
    }

    @Override
    public void onBindViewHolder(CatgoryViewHolder holder, int position) {
        CampaignCategory homeCampaign = mDatas.get(position);
        holder.textTitle.setText(homeCampaign.getTitle());

        imgService.LoadImg(holder.imageViewBig, homeCampaign.getCpOne().getImgUrl());
        imgService.LoadImg(holder.imageViewSmallTop,homeCampaign.getCpTwo().getImgUrl());
        imgService.LoadImg(holder.imageViewSmallBottom,homeCampaign.getCpThree().getImgUrl());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (position % 2 == 0) {
            return VIEW_TYPE_R;
        } else {
            return VIEW_TYPE_L;
        }
    }

    //单个item的保存holder
    static class CatgoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textTitle;
        private ImageView imageViewBig;
        private ImageView imageViewSmallTop;
        private ImageView imageViewSmallBottom;
        private HomeCatgoryRecyclerAdapter adapter;

        public CatgoryViewHolder(View itemView, HomeCatgoryRecyclerAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            textTitle = (TextView) itemView.findViewById(R.id.home_cardview_text_title);
            imageViewBig = (ImageView) itemView.findViewById(R.id.home_cardview_imgview_big);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.home_cardview_imgview_small_top);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.home_cardview_imgview_small_bottom);
            imageViewBig.setOnClickListener(this);
            imageViewSmallTop.setOnClickListener(this);
            imageViewSmallBottom.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != adapter && adapter.mListener != null) {
                anim(v);
            }
        }

        //点击添加一个图片翻转的属性动画
        private void anim(final View v) {

            ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotationX", 0.0F, 360.0F)
                    .setDuration(200);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {

                    CampaignCategory campaign = adapter.mDatas.get(getLayoutPosition());

                    switch (v.getId()) {
                        case R.id.home_cardview_imgview_big:
                            adapter.mListener.onClick(v, campaign.getCpOne());
                            break;

                        case R.id.home_cardview_imgview_small_top:
                            adapter.mListener.onClick(v, campaign.getCpTwo());
                            break;

                        case R.id.home_cardview_imgview_small_bottom:
                            adapter.mListener.onClick(v, campaign.getCpThree());
                            break;
                    }

                }
            });
            animator.start();
        }
    }

    //点击响应活动卡片接口
    public interface OnCampaignCardClickListener {
        void onClick(View view, CampaignCard campaign);
    }
}
