package com.kkt1019.tpmylocationkakao.network

import com.kkt1019.tpmylocationkakao.model.KakaoSearchPlaceResponse
import com.kkt1019.tpmylocationkakao.model.NaverUserInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApiService {

    //네아로 사용자정보 API
    @GET("/v1/nid/me")
    fun getNidUserInfo(@Header("Authorization") authorization:String):Call<NaverUserInfoResponse>

    //카카오 키워드 장소검색 API - 결과를 json 파싱한 kakaoSearchPlaceResponse 로
    @Headers("Authorization: KakaoAK 357c2def3b83e7d07a85a0279352c9f8")
    @GET("/v2/local/search/keyword.json")
    fun searchPlaces(@Query("query")query:String, @Query("x")longitude:String, @Query("y")latitude:String):Call<KakaoSearchPlaceResponse>

    //카카오 키워드 장소검색 API - 결과를 String 으로
    @Headers("Authorization: KakaoAK 357c2def3b83e7d07a85a0279352c9f8")
    @GET("/v2/local/search/keyword.json")
    fun searchPlacesToString(@Query("query")query:String, @Query("x")longitude:String, @Query("y")latitude:String):Call<String>

}