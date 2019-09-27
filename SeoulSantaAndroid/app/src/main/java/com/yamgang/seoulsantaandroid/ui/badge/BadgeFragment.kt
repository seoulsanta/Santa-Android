package com.yamgang.seoulsantaandroid.ui.badge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.ui.home.HomeData
import com.yamgang.seoulsantaandroid.util.ApplicationController
import kotlinx.android.synthetic.main.fragment_badge.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class BadgeFragment : Fragment() {
    lateinit var inflater: LayoutInflater
    val networkService = ApplicationController.networkService

    //lateinit var data: BadgeData
    var data: BadgeData = BadgeData(4,10,
        arrayListOf(BadgeListData(1,1,"댕댕이코스","몇월몇일"),
            BadgeListData(1,1,"댕댕이코스","몇월몇일"),
            BadgeListData(1,1,"댕댕이코스","몇월몇일"),
            BadgeListData(1,1,"댕댕이코스","몇월몇일"))
    )

    companion object{
        var instance: BadgeFragment = BadgeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.inflater = inflater
        var view: View = inflater.inflate(R.layout.fragment_badge, container, false)
        viewInit(view)

        //getBadgeResponse()
        instance.data = data

        return view
    }

    private fun viewInit(view:View) {
        view.vp_frag_badge.offscreenPageLimit = 2
        view.vp_frag_badge.adapter = BadgeFragmentStatePagerAdapter(childFragmentManager,2)
        view.indicator_frag_badge.setViewPager(view.vp_frag_badge)
    }

    private fun getBadgeResponse() {
        val getBadgeResponse = networkService.getBadgeResponse("application/json","")
        getBadgeResponse.enqueue(object : Callback<GetBadgeResponse> {
            override fun onFailure(call: Call<GetBadgeResponse>, t: Throwable) {
                Log.e("HomeFragment-fail", t.toString())
            }

            override fun onResponse(call: Call<GetBadgeResponse>, response: Response<GetBadgeResponse>) {
                if(response.isSuccessful) {
                    try{
                        instance.data = response.body()!!.data
                    } catch (e: Exception) {
                    }
                }
            }
        })
    }

}
