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

    fun clickEvent(v:View){
        v.mt_1.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

            viewCourses(1)

        }
        v.mt_2.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

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
