package com.aerozhonghuan.demo.mvp.model.retrofit;

import com.aerozhonghuan.demo.mvp.Configuration;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 设置请求接口
 * <p>
 * * @QueryMap注解会把参数拼接到url后面，所以它适用于GET请求；
 * * @Body会把参数放到请求体中，所以适用于POST请求。
 */

public interface RetrofitApi {

    @GET("qingqi/product/login")
    Call<ResponseBody> loginGet(@QueryMap Map<String, Object> hashMap);

    @GET("qingqi/product/carPageList")
    Call<ResponseBody> getCars(@QueryMap Map<String, Object> hashMap);

    @GET("qingqi/product/updateUserInfo")
    Call<ResponseBody> updateInfo(@QueryMap Map<String, Object> hashMap);

    @POST("api/hongyan/serverstation/login")
    Call<ResponseBody> loginPost(@Body RequestBody requestBody);

}

