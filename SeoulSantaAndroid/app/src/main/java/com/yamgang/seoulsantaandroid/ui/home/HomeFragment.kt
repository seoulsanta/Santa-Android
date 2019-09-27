package com.yamgang.seoulsantaandroid.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.ui.MyActivity
import com.yamgang.seoulsantaandroid.util.ApplicationController
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class HomeFragment : Fragment() {

    var dataList: ArrayList<HomeThemeData> = ArrayList()
    var data : HomeData = HomeData("흐림","즐거운 등산되시던가~",dataList)
    lateinit var homeThemeRecyclerViewAdapter: HomeThemeRecyclerViewAdapter
    val networkService = ApplicationController.networkService

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setOnClickListener()
        getHomeResponse()
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
