package com.yamgang.seoulsantaandroid.util

import com.yamgang.seoulsantaandroid.model.get.GetCourse
import com.yamgang.seoulsantaandroid.model.get.GetCourseDetail
import com.yamgang.seoulsantaandroid.model.get.GetMountain
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

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


}