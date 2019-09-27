package com.yamgang.seoulsantaandroid.util

import com.yamgang.seoulsantaandroid.ui.badge.GetBadgeResponse
import com.yamgang.seoulsantaandroid.ui.home.GetHomeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface NetworkService {
    //홈
    @GET("/home")
    fun getHomeResponse(
        @Header("Content-Type") content_type: String
    ): Call<GetHomeResponse>

    //뱃지 조회
    @GET("/user/badge")
    fun getBadgeResponse(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") token: String
    ): Call<GetBadgeResponse>

}