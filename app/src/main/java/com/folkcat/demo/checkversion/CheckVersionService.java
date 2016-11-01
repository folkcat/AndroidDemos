package com.folkcat.demo.checkversion;


import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Tamas on 2016/11/1.
 */

public interface CheckVersionService {
    @GET("check_version3.json")
    Call<CheckVersion> getLastVersionInfo();

}
