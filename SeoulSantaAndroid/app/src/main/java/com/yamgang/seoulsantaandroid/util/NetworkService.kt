package com.yamgang.seoulsantaandroid.util

import com.yamgang.seoulsantaandroid.model.get.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    //맵
    //1.산 조회
    @GET("/mountain/{mountain_idx}")
    fun getMountain(@Header("Content-Type") contentType: String = "application/json",
                          @Path("mountain_idx")mountain_idx:Int): Call<GetMountain>

    //2.코스 조회
    @GET("/course/{course_idx}")
    fun getCourse(@Header("Content-Type") contentType: String = "application/json",
                          @Path("course_idx")course_idx:Int):Call<GetCourse>

    //3.코스 길 조회
    @GET("/course/{course_idx}/line")
    fun getCourseDetail(@Header("Content-Type") contentType: String = "application/json",
                  @Path("course_idx")course_idx:Int):Call<GetCourseDetail>

    //서치
    //1.  산 검색
    @GET("/mountain/search")
    fun getSearchMountain(
        @Header("Content-Type") contentType: String = "application/json",
        @Query("query") query: String
    ):Call<GetSearchMountain>

    //2. 추천 검색어
    @GET("/mountain/recommend")
    fun getMountainRecommend(
        @Header("Content-Type") contentType: String = "application/json"
    ):Call<GetMountainRecommend>
}