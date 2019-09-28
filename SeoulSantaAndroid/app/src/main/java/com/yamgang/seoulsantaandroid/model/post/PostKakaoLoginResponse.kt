package com.yamgang.seoulsantaandroid.model.post

data class PostKakaoLoginResponse(
    val status: Int,
    val message: String,
    val data: LoginData
)

data class LoginData(
    val authorization: String,
    val refreshtoken: String
)