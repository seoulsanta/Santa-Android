package com.yamgang.seoulsantaandroid.model.get

import com.yamgang.seoulsantaandroid.model.LongAndLat

data class GetCourseDetail (
    val message : String,
    val data : ArrayList<LongAndLat>
)