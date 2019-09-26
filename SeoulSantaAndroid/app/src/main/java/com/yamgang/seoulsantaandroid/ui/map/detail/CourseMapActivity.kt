package com.yamgang.seoulsantaandroid.ui.map.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.daum.mf.map.api.MapView
import android.view.ViewGroup
import com.yamgang.seoulsantaandroid.R
import kotlinx.android.synthetic.main.activity_course_map.view.*
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPolyline




class CourseMapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_map)

        val mapView = MapView(this)
        mapView.setDaumMapApiKey(" 8ed4eb623bf3c05efba5867b5af63929")

        val mapViewContainer = findViewById<ViewGroup>(R.id.mapview)
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
        mapViewContainer.addView(mapView)


        val polyline = MapPolyline()
      // polyline.addPoint(MapPoint.mapPointWithGeoCoord())



    }
}
