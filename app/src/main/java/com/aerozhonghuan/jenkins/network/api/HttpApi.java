package com.aerozhonghuan.jenkins.network.api;

import java.util.HashMap;

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

public interface HttpApi {

    @GET(Url.get + "login")
    Call<ResponseBody> loginGet(@QueryMap HashMap<String, String> hashMap);

    @GET(Url.get + "carPageList")
    Call<ResponseBody> carPageList(@QueryMap HashMap<String, Object> hashMap);

    @POST(Url.post + "login")
    Call<ResponseBody> loginPost(@Body RequestBody requestBody);
}

