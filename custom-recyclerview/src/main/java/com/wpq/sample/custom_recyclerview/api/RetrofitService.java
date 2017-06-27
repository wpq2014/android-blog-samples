package com.wpq.sample.custom_recyclerview.api;

import com.wpq.sample.custom_recyclerview.bean.GanHuo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

//    @GET("api/data/{type}/{count}/{page}")
//    Call<GanHuo> getGanHuo(@Path("type") String type, @Path("count") int count, @Path("page") int page);

    @GET("api/data/{type}/{count}/{page}")
    Observable<GanHuo> getGanHuo(@Path("type") String type, @Path("count") int count, @Path("page") int page);

    @GET("show.htm")
    Observable<String> getGirls(@Query("cid") String cid, @Query("pager_offset") int pager_offset);
}
