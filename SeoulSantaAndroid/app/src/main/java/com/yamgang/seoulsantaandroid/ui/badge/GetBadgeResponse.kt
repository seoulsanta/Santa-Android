package com.yamgang.seoulsantaandroid.ui.badge

data class GetBadgeResponse (
    val message: String,
    val data: BadgeData
)

data class BadgeData (
    val cnt: Int,
    val total: Int,
    val badge: ArrayList<BadgeListData>
)

data class BadgeListData(
    val badge_idx: Int,
    val course_idx: Int,
    val course_name: String,
    val date: String
)