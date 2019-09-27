package com.yamgang.seoulsantaandroid.ui.map


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.model.Course
import com.yamgang.seoulsantaandroid.model.Courses
import com.yamgang.seoulsantaandroid.model.get.GetMountain
import com.yamgang.seoulsantaandroid.ui.map.adapter.MapRVAdapter
import com.yamgang.seoulsantaandroid.util.ApplicationController
import com.yamgang.seoulsantaandroid.util.NetworkService
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_map.view.*
import kotlinx.android.synthetic.main.fragment_map.view.rcv_map
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapFragment : Fragment() {

    lateinit var networkService: NetworkService
    lateinit var mapRVAdapter: MapRVAdapter
    var dataList: ArrayList<Courses> = ArrayList()

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_map, container, false)
        networkService = ApplicationController.networkService

        clickEvent(v)

        return v
    }

    /*
    1.불암산
    2.수락산
    3.도봉산
    4.북한산
    5.북악산
    6.인왕산
    7.안산
    8.백련산
    9.봉제산
    10.용마산
    11.관악산
    12.우면산
    13.구룡산
    14.남산
    15.아차산
     */

    fun clickEvent(v:View){
        v.bulam.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

            v.dw_bulam.visibility = View.VISIBLE

            viewCourses(1)

        }
        v.sulak.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

            v.dw_bulam.visibility = View.INVISIBLE
            v.dw_sulak.visibility = View.VISIBLE

            viewCourses(2)

        }
        v.dobong.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

            viewCourses(3)

        }
        v.bukhan.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

            viewCourses(4)

        }
        v.bukak.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

            viewCourses(5)

        }
        v.inwang.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

            viewCourses(6)

        }
        v.ansan.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

            viewCourses(7)

        }
        v.baengnyeon.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

            viewCourses(8)

        }
        v.bongge.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

            viewCourses(9)

        }
        v.yongma.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

            viewCourses(10)

        }
        v.gwanak.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

            viewCourses(11)

        }
        v.umyeon.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

            viewCourses(12)

        }
        v.guryong.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

            viewCourses(13)

        }
        v.namsan.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

            viewCourses(14)

        }
        v.acha.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

            viewCourses(15)

        }

        //요런식으로 산 15개(?) 까지..

    }

    fun viewCourses(mountain_idx : Int){
        val getMountain = networkService.getMountain("application/json",mountain_idx)
        getMountain.enqueue(object : Callback<GetMountain>{
            override fun onFailure(call: Call<GetMountain>, t: Throwable) {
            }

            override fun onResponse(call: Call<GetMountain>, response: Response<GetMountain>) {
                if(response.isSuccessful){
                    dataList = response.body()!!.data.course
                    setRV()

                }else{

                }
            }

        })

    }

    fun setRV(){
        mapRVAdapter = MapRVAdapter(activity!!,dataList)
        rcv_map.adapter = mapRVAdapter
        rcv_map.layoutManager = LinearLayoutManager(activity!!,RecyclerView.VERTICAL,false)
    }

}
