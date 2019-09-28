package com.yamgang.seoulsantaandroid.ui.badge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.model.get.BadgeData
import com.yamgang.seoulsantaandroid.model.get.BadgeListData
import com.yamgang.seoulsantaandroid.model.get.GetBadgeResponse
import com.yamgang.seoulsantaandroid.ui.badge.current.BadgeCurrentFragment
import com.yamgang.seoulsantaandroid.ui.badge.current.BadgeCurrentRecyclerViewAdapter
import com.yamgang.seoulsantaandroid.ui.badge.finish.BadgeFinishFragment
import com.yamgang.seoulsantaandroid.util.ApplicationController
import com.yamgang.seoulsantaandroid.util.User
import kotlinx.android.synthetic.main.fragment_badge.*
import kotlinx.android.synthetic.main.fragment_badge.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class BadgeFragment : Fragment() {
    val networkService = ApplicationController.networkService
    var data: BadgeData = BadgeData(-1,-1,ArrayList())
    companion object{
        var instance: BadgeFragment = BadgeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_badge, container, false)
        viewInit(view)
        getBadgeResponse(view)
        Log.e("BadgeFragment-success", instance.data.toString())
        return view
    }

    private fun viewInit(view:View) {
        view.vp_frag_badge.offscreenPageLimit = 2
        view.vp_frag_badge.adapter = BadgeFragmentStatePagerAdapter(childFragmentManager,2)
        view.indicator_frag_badge.setViewPager(view.vp_frag_badge)
    }

    private fun getBadgeResponse(view:View) {
        val getBadgeResponse = networkService.getBadgeResponse("application/json", User.authorization)
        getBadgeResponse.enqueue(object : Callback<GetBadgeResponse> {
            override fun onFailure(call: Call<GetBadgeResponse>, t: Throwable) {
                Log.e("BadgeFragment-fail", t.toString())
            }

            override fun onResponse(call: Call<GetBadgeResponse>, response: Response<GetBadgeResponse>) {
                if(response.isSuccessful) {
                    try{
                        instance.data = response.body()!!.data
                        Log.e("BadgeFragment-success1", instance.data.toString())
                        (view.vp_frag_badge.adapter as BadgeFragmentStatePagerAdapter).notifyDataSetChanged()
                    } catch (e: Exception) {
                    }
                }
            }
        })
    }
}
