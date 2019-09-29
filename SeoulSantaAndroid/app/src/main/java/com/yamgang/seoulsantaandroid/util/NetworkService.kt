package com.yamgang.seoulsantaandroid.util

import com.yamgang.seoulsantaandroid.model.BadgeRegister
import com.yamgang.seoulsantaandroid.model.put.PutMypageEditResponse
import com.yamgang.seoulsantaandroid.model.get.*
import com.yamgang.seoulsantaandroid.model.post.PostBadgeRegister
import com.yamgang.seoulsantaandroid.model.post.PostKakaoLoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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
        @Header("Authorization") token: String?
    ): Call<GetBadgeResponse>

    //뱃지 등록
    @POST("/user/badge")
    fun postBadgeRegister(@Header("Content-Type") contentType: String = "application/json",
                          @Header("Authorization") token: String?,
                          @Body badgeRegister: BadgeRegister ):Call<PostBadgeRegister>


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

    //카카오 로그인
    @POST("/login/kakao")
    fun postKakaoLoginResponse(
        @Header("Content-Type") content_type: String?,
        @Header("accesstoken") token: String?
    ): Call<PostKakaoLoginResponse>


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

    //마이페이지 조회
    @GET("/user/mypage")
    fun getMypageResponse(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") token: String?
    ):Call<GetMypageResponse>

    //마이페이지 수정
    @Multipart
    @PUT("/user/mypage")
    fun putMypageEditResponse(
        @Header("Authorization") token: String?,
        @Part("name") name: RequestBody,
        @Part img: MultipartBody.Part?
    ):Call<PutMypageEditResponse>
}