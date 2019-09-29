package com.yamgang.seoulsantaandroid.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.model.get.GetHomeResponse
import com.yamgang.seoulsantaandroid.model.get.HomeData
import com.yamgang.seoulsantaandroid.model.get.HomeThemeData
import com.yamgang.seoulsantaandroid.ui.my.MyActivity
import com.yamgang.seoulsantaandroid.util.ApplicationController
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class HomeFragment : Fragment() {

    var dataList: ArrayList<HomeThemeData> = ArrayList()
    var data : HomeData =
        HomeData("", "","","","", dataList)
    lateinit var homeThemeRecyclerViewAdapter: HomeThemeRecyclerViewAdapter
    val networkService = ApplicationController.networkService
    val fadeIn1 = AlphaAnimation(0.0f, 1.0f)
    val fadeOut1 = AlphaAnimation(1.0f, 0.0f)
    val fadeIn2 = AlphaAnimation(0.0f, 1.0f)
    val fadeOut2 = AlphaAnimation(1.0f, 0.0f)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setOnClickListener()
        getHomeResponse()
        setAnimation1()
        setAnimation2()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun setRecyclerView() {
        homeThemeRecyclerViewAdapter =
            HomeThemeRecyclerViewAdapter(activity!!, dataList)
        rv_frag_home_theme_list.adapter = homeThemeRecyclerViewAdapter
        rv_frag_home_theme_list.layoutManager = LinearLayoutManager(activity)
    }

    private fun setOnClickListener(){
        btn_frag_home_my.setOnClickListener {
            val intent = Intent(context, MyActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setWeather() {
        var img : Int = -1
        if(data.weather == "맑음") {
            img = R.drawable.icon_sun
        } else if(data.weather == "흐림") {
            img = R.drawable.icon_cloud
        } else if(data.weather == "비") {
            img = R.drawable.icon_rain
        } else if(data.weather == "눈") {
            img = R.drawable.icon_snow
        } else if(data.weather == "안개") {
            img = R.drawable.icon_fog
        } else if(data.weather == "황사") {
            img = R.drawable.icon_fog
        }
        Glide.with(activity!!)
            .load(img)
            .into(iv_frag_home_weather)
        tv_frag_home_weather.text = data.weather+"입니다."
        tv_frag_home_text.text = data.text
        tv_frag_home_dust.text = data.dust+"입니다. ("+data.dust_num+"㎍/㎥)"
        if(data.dust =="최고" || data.dust == "좋음") {
            view_frag_home_dust_color.setBackgroundColor(resources.getColor(R.color.homeDust1))
        } else if(data.dust =="양호" || data.dust == "보통") {
            view_frag_home_dust_color.setBackgroundColor(resources.getColor(R.color.homeDust2))
        } else if(data.dust =="나쁨" || data.dust == "상당히 나쁨") {
            view_frag_home_dust_color.setBackgroundColor(resources.getColor(R.color.homeDust3))
        } else if(data.dust =="매우 나쁨" || data.dust == "최악") {
            view_frag_home_dust_color.setBackgroundColor(resources.getColor(R.color.homeDust4))
        }
        val width1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,72.toFloat(),resources.displayMetrics).toInt()
        val width2 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,60.toFloat(),resources.displayMetrics).toInt()
        val width3 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,31.toFloat(),resources.displayMetrics).toInt()

        if(data.dust == "상당히 나쁨") {
            view_frag_home_dust_color.layoutParams.width = width1
        } else if(data.dust =="매우 나쁨") {
            view_frag_home_dust_color.layoutParams.width = width2
        } else {
            view_frag_home_dust_color.layoutParams.width = width3
        }
        tv_frag_home_dust_ment.text = data.dust_text
    }

    private fun setAnimation1() {
        fadeIn1.duration = 700
        fadeIn1.fillAfter = true
        fadeIn1.startOffset = 3000
        fadeIn1.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                ll_frag_home_first_text.startAnimation(fadeOut1)
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })

        fadeOut1.duration = 700
        fadeOut1.fillAfter = true
        fadeOut1.startOffset = 3000
        fadeOut1.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                ll_frag_home_first_text.startAnimation(fadeIn1)
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })
        ll_frag_home_first_text.startAnimation(fadeIn1)
    }

    private fun setAnimation2() {
        fadeOut2.duration = 700
        fadeOut2.fillAfter = true
        fadeOut2.startOffset = 3000
        fadeOut2.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                ll_frag_home_second_text.startAnimation(fadeIn2)
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })

        fadeIn2.duration = 700
        fadeIn2.fillAfter = true
        fadeIn2.startOffset = 3000
        fadeIn2.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                ll_frag_home_second_text.startAnimation(fadeOut2)
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })

        ll_frag_home_second_text.startAnimation(fadeOut2)
    }

    private fun getHomeResponse() {
        val getHomeResponse = networkService.getHomeResponse("application/json")
        getHomeResponse.enqueue(object : Callback<GetHomeResponse> {
            override fun onFailure(call: Call<GetHomeResponse>, t: Throwable) {
                Log.e("HomeFragment-fail", t.toString())
            }

            override fun onResponse(call: Call<GetHomeResponse>, response: Response<GetHomeResponse>) {
                if(response.isSuccessful) {
                    try{
                        dataList = response.body()!!.data.theme
                        data = response.body()!!.data
                        setWeather()
                        setRecyclerView()
                    } catch (e:Exception) {
                    }
                }
            }
        })
    }
}
