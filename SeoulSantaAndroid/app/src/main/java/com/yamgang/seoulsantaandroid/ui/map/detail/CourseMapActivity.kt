package com.yamgang.seoulsantaandroid.ui.map.detail

import android.Manifest
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
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yamgang.seoulsantaandroid.model.BadgeRegister
import com.yamgang.seoulsantaandroid.model.post.PostBadgeRegister
import com.yamgang.seoulsantaandroid.ui.MainActivity
import com.yamgang.seoulsantaandroid.util.User


class CourseMapActivity : AppCompatActivity() {

    lateinit var networkService: NetworkService
    var dataList: ArrayList<LongAndLat> = ArrayList()
    lateinit var mapView: MapView
    lateinit var lm:LocationManager

    private val GPS_ENABLE_REQUEST_CODE = 2001
    private val PERMISSIONS_REQUEST_CODE = 100

    private val REQUIRED_PERMISSIONS  = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)



    var flag = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_map)

        mapView = MapView(this)
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOff

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting()
        }else {

            checkRunTimePermission()
        }



        networkService = ApplicationController.networkService

        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager


        val course_idx = intent.getIntExtra("course_idx",-1)
        val course_name = intent.getStringExtra("name")
        val badge_flag = intent.getIntExtra("badge",-1) //뱃지에서 오는거면 1, 아니면 0



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
        ((btn_call_119.parent) as ViewGroup).removeView(btn_call_119)

        mapViewContainer.addView(mapView)

        mapViewContainer.addView(course_layout)

        if(badge_flag==1){
            map_course_name.text = "$course_name 완주!"

        }else{
            map_course_name.text = "$course_name 도전중"

            mapViewContainer.addView(btn_end)

            mapViewContainer.addView(btn_crr)

            mapViewContainer.addView(btn_call_119)
        }



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
            endHiking(course_idx)
        }
        
        // 신고 버튼 -> 119이 써진 다이얼로그 화면 띄우기
        btn_call_119.setOnClickListener {
            call119()
        }

    }

    fun endHiking(course_idx: Int){

       var location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER) //빨간줄 무시해라
        if(location!=null){
            val long = location.longitude
            val lat = location.latitude

            if((lat>=dataList[dataList.size-1].latitude-0.001&&lat<=dataList[dataList.size-1].latitude+0.001)&&
                (long>=dataList[dataList.size-1].longitude-0.001&&long<=dataList[dataList.size-1].longitude+0.001)){
                Toast.makeText(applicationContext,"완주를 축하합니다! 뱃지가 등록되었습니다.",Toast.LENGTH_SHORT).show()
                //뱃지등록 API 넣기@@@@@@@@@@@@@@@@@@@
                badgeRegister(course_idx,User.authorization)

            }else{

                Toast.makeText(applicationContext,"아직 목표지점에 도착하지 않았습니다.",Toast.LENGTH_SHORT).show()
            }

            //Toast.makeText(applicationContext,"long:"+dataList[0].longitude+" lat:"+dataList[0].latitude,Toast.LENGTH_SHORT).show()
          //  Toast.makeText(applicationContext,"long:"+long.toString()+"l at:"+lat.toString(),Toast.LENGTH_SHORT).show()
        }


    }

    fun badgeRegister(course_idx: Int,token:String?){
        val courseIdx = BadgeRegister(course_idx)
        val postBadgeRegister = networkService.postBadgeRegister("application/json",token,courseIdx)
        postBadgeRegister.enqueue(object:Callback<PostBadgeRegister>{
            override fun onFailure(call: Call<PostBadgeRegister>, t: Throwable) {

            }

            override fun onResponse(call: Call<PostBadgeRegister>, response: Response<PostBadgeRegister>) {
                if(response.isSuccessful){
                    Toast.makeText(applicationContext,"호쨔~",Toast.LENGTH_SHORT).show()


                }else{

                }

            }

        })
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
                    polyline.lineColor = Color.parseColor("#E97424")
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {


        if ( requestCode == PERMISSIONS_REQUEST_CODE && grantResults.size == REQUIRED_PERMISSIONS.size) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            var check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

             for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false
                    break
                }
            }

            if ( check_result ) {

                //위치 값을 가져올 수 있음
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(applicationContext, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                }else {

                    Toast.makeText(applicationContext, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    fun checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(this@CourseMapActivity,
                Manifest.permission.ACCESS_FINE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@CourseMapActivity, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(this@CourseMapActivity, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(this@CourseMapActivity, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE)


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(this@CourseMapActivity, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE)
            }

        }

    }

    fun call119(){
        val tel = "tel:119"
        val intent = Intent("android.intent.action.DIAL", Uri.parse(tel))
        startActivity(intent)
    }

     //여기부터는 GPS 활성화를 위한 메소드들
    fun showDialogForLocationServiceSetting() {

        val builder = AlertDialog.Builder(this@CourseMapActivity)
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
         builder.setPositiveButton("설정") { dialogInterface, i ->
             val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
             startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
         }
         builder.setNegativeButton("취소") { dialogInterface, i ->
             dialogInterface.cancel()
         }

         builder.create().show()
    }

    fun checkLocationServicesStatus() : Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}
