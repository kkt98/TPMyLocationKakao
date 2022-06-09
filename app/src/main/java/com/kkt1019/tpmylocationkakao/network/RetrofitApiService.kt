package com.kkt1019.tpmylocationkakao.network

import com.kkt1019.tpmylocationkakao.model.NaverUserInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface RetrofitApiService {

    //네아로 사용자정보 API
    @GET("/v1/nid/me")
    fun getNidUserInfo(@Header("Authorization") authorization:String):Call<NaverUserInfoResponse>

}