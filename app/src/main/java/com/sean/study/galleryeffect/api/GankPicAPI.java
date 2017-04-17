package com.sean.study.galleryeffect.api;

import com.sean.study.galleryeffect.bean.GankPicBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/4/17 0017.
 */

public interface GankPicAPI {

    @GET("data/福利/{amount}/{page}")
    Observable<GankPicBean> httpsGetPicRx(@Path("amount") int amount, @Path("page") int page);
}
