package com.yamgang.seoulsantaandroid.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.ui.MyActivity
import com.yamgang.seoulsantaandroid.ui.home.Adapter.HomeThemeRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    val dataList: ArrayList<HomeThemeData> = arrayListOf(
        HomeThemeData(1,"혜진현무 코스",
            arrayListOf(HomeCourseData(1,"어쩌구산",R.drawable.mt_namsan00),
                HomeCourseData(2,"저쩌구산",R.drawable.mt_namsan00),
                HomeCourseData(3,"뭐라구산",R.drawable.mt_namsan00))),
        HomeThemeData(2,"초보자용 코스",
            arrayListOf(HomeCourseData(4,"어쩌구산",R.drawable.mt_namsan00),
                HomeCourseData(5,"저쩌구산",R.drawable.mt_namsan00),
                HomeCourseData(6,"뭐라구산",R.drawable.mt_namsan00))),
        HomeThemeData(3,"한시간이면 정상!",
            arrayListOf(HomeCourseData(7,"어쩌구산",R.drawable.mt_namsan00),
                HomeCourseData(8,"저쩌구산",R.drawable.mt_namsan00),
                HomeCourseData(9,"뭐라구산",R.drawable.mt_namsan00)))
    )
    val data : HomeData = HomeData("맑음","즐거운 등산되라능~!",dataList)
    //val dataList: ArrayList<HomeThemeData> = ArrayList()
    lateinit var homeThemeRecyclerViewAdapter: HomeThemeRecyclerViewAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()
        setOnClickListener()
        setWeather()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun setRecyclerView() {
        homeThemeRecyclerViewAdapter = HomeThemeRecyclerViewAdapter(activity!!,dataList)
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
        tv_frag_home_weather.text = data.weather+"입니다."
        tv_frag_home_text.text = data.text
    }

}
