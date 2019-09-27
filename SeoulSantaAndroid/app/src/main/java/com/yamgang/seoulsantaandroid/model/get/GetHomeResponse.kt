package com.yamgang.seoulsantaandroid.model.get

data class GetHomeResponse (
    var message: String,
    var data: HomeData
)

data class HomeData (
    var weather: String,
    var text: String,
    var theme: ArrayList<HomeThemeData>
)

data class HomeThemeData(
    var theme_idx: Int,
    var theme_name: String,
    var course: ArrayList<HomeCourseData>
)

data class HomeCourseData(
    var course_idx: Int,
    var mountain_name: String,
    var mountain_img: String
)