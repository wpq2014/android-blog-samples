package com.wpq.sample.custom_recyclerview.api;

import com.wpq.sample.custom_recyclerview.bean.GanHuo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {

    @GET("api/data/{type}/{count}/{page}")
    Call<GanHuo> getGanHuo(@Path("type") String type, @Path("count") int count, @Path("page") int page);
}
