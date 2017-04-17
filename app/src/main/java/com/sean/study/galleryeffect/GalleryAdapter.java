package com.sean.study.galleryeffect;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sean.study.galleryeffect.bean.GankPicBean;
import com.sean.study.galleryeffect.helper.CardAdapterHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/15 0015.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {


    private Activity mActivity;
    private List<GankPicBean.ResultsBean> mListData = new ArrayList<>();
    private CardAdapterHelper mAdapterHelper = new CardAdapterHelper();
    public GalleryAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void setData(List<GankPicBean.ResultsBean> mListData){
        this.mListData = mListData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.recycler_item_gallery, parent, false);
        mAdapterHelper.onCreateViewHolder(parent,view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mAdapterHelper.onBindViewHolder(holder.itemView,position,getItemCount());
        Glide.with(mActivity).load(mListData.get(position).getUrl()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imgItem);
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_item)
        ImageView imgItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
