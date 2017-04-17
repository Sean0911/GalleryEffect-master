package com.sean.study.galleryeffect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sean.study.galleryeffect.api.GankPicAPI;
import com.sean.study.galleryeffect.bean.GankPicBean;
import com.sean.study.galleryeffect.helper.CardScaleHelper;
import com.sean.study.galleryeffect.httputils.RetrofitClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_gallery)
    RecyclerView recyclerGallery;

    private GalleryAdapter mAdapter;
    private CardScaleHelper mScaleHelper;
    private int mLastVisibleItem;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new GalleryAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerGallery.setLayoutManager(linearLayoutManager);
        recyclerGallery.setHasFixedSize(true);
        recyclerGallery.setAdapter(mAdapter);

        mScaleHelper = new CardScaleHelper();
        mScaleHelper.setCurrentItemPos(2);
        mScaleHelper.attachToRecyclerView(recyclerGallery);
        loadData();

        recyclerGallery.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个可见条目的角标
                    mLastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void loadData() {
        RetrofitClient.createService(GankPicAPI.class)
                .httpsGetPicRx(50,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankPicBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankPicBean value) {
                        if (!value.isError()) {
                            mAdapter.setData(value.getResults());
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
