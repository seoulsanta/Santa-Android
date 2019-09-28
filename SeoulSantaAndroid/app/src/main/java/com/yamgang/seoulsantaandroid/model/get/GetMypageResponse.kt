package com.yamgang.seoulsantaandroid.model.get

data class GetMypageResponse (
    val message: String,
    val data: MyData
)

data class MyData (
    val name: String,
    val img: String
)