package com.huaweisoft.retrofitdemo;


import com.huaweisoft.retrofitdemo.bean.ArticleList;
import com.huaweisoft.retrofitdemo.bean.LoginBean;
import com.huaweisoft.retrofitdemo.config.UrlConfig;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author baiaj
 * 接口描述类
 */
public interface ApiService {

    /**
     * 获取公众号列表
     */
    @GET(UrlConfig.GET_ARTICLE_LIST)
    Call<ArticleList> getArticleList();

    @POST(UrlConfig.LOGIN)
    @FormUrlEncoded
    Call<LoginBean> login(@Field("username") String name,@Field("password") String password);

}
