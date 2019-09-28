package com.yamgang.seoulsantaandroid.model.get

data class GetSearchMountain(
    val message : String,
    val data : SearchMountainData
)

data class SearchMountainData(
    val mountain_idx : Int,
    val mountain_name : String,
    val mountain_altitude : Int,
    val course : ArrayList<SearchMountainCourseData>
)

data class SearchMountainCourseData(
    val theme_name : String,
    val course_idx : Int,
    val course_name : String,
    val course_img : String
)