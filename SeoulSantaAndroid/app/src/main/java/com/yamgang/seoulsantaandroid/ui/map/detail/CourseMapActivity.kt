package com.yamgang.seoulsantaandroid.ui.map.detail

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import net.daum.mf.map.api.MapView
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.model.LongAndLat
import com.yamgang.seoulsantaandroid.model.get.GetCourseDetail
import com.yamgang.seoulsantaandroid.util.ApplicationController
import com.yamgang.seoulsantaandroid.util.NetworkService
import kotlinx.android.synthetic.main.activity_course_map.*
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPolyline
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import net.daum.mf.map.api.CameraUpdateFactory
import net.daum.mf.map.api.MapPointBounds
import net.daum.mf.map.api.MapPOIItem






class CourseMapActivity : AppCompatActivity() {

    lateinit var networkService: NetworkService
    var dataList: ArrayList<LongAndLat> = ArrayList()
    lateinit var mapView: MapView
    var flag = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_map)

        networkService = ApplicationController.networkService

        val course_idx = intent.getIntExtra("course_idx",-1)
        val course_name = intent.getStringExtra("name")

        mapView = MapView(this)

        val btn_crr = ImageView(this)
        val layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.leftMargin = 20
        layoutParams.bottomMargin = 21
        btn_crr.layoutParams = layoutParams
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,1)
        btn_crr.setImageResource(R.drawable.icon_crr)



        mapView.setDaumMapApiKey(" 8ed4eb623bf3c05efba5867b5af63929")

        val mapViewContainer = findViewById<ViewGroup>(R.id.mapview)


        getCourselocation(course_idx)

        ((course_layout.parent) as ViewGroup).removeView(course_layout)
        ((btn_end.parent) as ViewGroup).removeView(btn_end)

        mapViewContainer.addView(mapView)

        mapViewContainer.addView(course_layout)
        map_course_name.text = course_name+" 도전중"

        mapViewContainer.addView(btn_end)

        mapViewContainer.addView(btn_crr)

        mapView.setCustomCurrentLocationMarkerTrackingImage(R.drawable.icon_point,MapPOIItem.ImageOffset(23,23))
        btn_crr.setOnClickListener {
            if(flag == 0){
                mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
                flag = 1
            }else if(flag == 1){
                mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOff
                flag = 0
            }

        }
        btn_end.setOnClickListener {
            //등산 끝내기 눌렀을 때 -> current location이 저장된 데이터의 마지막인덱스의 위도 경도 의 일정범위내에 속하는지 확인
        }




    }

    fun getCourselocation(course_idx:Int){

        val getCourseDetail = networkService.getCourseDetail("application/json",course_idx)
        getCourseDetail.enqueue(object : Callback<GetCourseDetail>{
            override fun onFailure(call: Call<GetCourseDetail>, t: Throwable) {

            }

            override fun onResponse(call: Call<GetCourseDetail>, response: Response<GetCourseDetail>) {
                if(response.isSuccessful){
                    val polyline = MapPolyline()
                    polyline.tag = 1000
                    polyline.lineColor = Color.parseColor("#58be00")
                    dataList = response.body()!!.data

                    for (i in 0 until dataList.size){
                        polyline.addPoint(MapPoint.mapPointWithGeoCoord(dataList[i].latitude,dataList[i].longitude))
                    }
                    mapView.addPolyline(polyline)

                    // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
                    val mapPointBounds = MapPointBounds(polyline.mapPoints)
                    val padding = 100 // px
                    mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding))

                    val marker = MapPOIItem()
                    marker.itemName = "시작"
                    marker.tag = 0
                    marker.mapPoint = MapPoint.mapPointWithGeoCoord(dataList[0].latitude,dataList[0].longitude)
                    marker.markerType = MapPOIItem.MarkerType.CustomImage
                    marker.customImageResourceId = R.drawable.icon_round // 마커 이미지.
                    marker.isCustomImageAutoscale = false
                    marker.setCustomImageAnchor(0.5f, 0.5f)
//                    marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                    mapView.addPOIItem(marker)

                    val marker2 = MapPOIItem()
                    marker2.itemName = "끝"
                    marker2.tag = 1
                    marker2.mapPoint = MapPoint.mapPointWithGeoCoord(dataList[dataList.size-1].latitude,dataList[dataList.size-1].longitude)
                    marker2.markerType = MapPOIItem.MarkerType.CustomImage
                    marker2.customImageResourceId = R.drawable.icon_round // 마커 이미지.
                    marker2.isCustomImageAutoscale = false
                    marker2.setCustomImageAnchor(0.5f, 0.5f)
//                    marker2.selectedMarkerType = MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                    mapView.addPOIItem(marker2)


                }else{

                }
            }

        })

    }
}
