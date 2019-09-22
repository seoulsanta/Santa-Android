package com.yamgang.seoulsantaandroid.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.yamgang.seoulsantaandroid.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewInit()
    }

    private fun viewInit(){
        vp_main_act_pager.apply{
            adapter = MainTabFragmentStatePagerAdapter(supportFragmentManager, 4)
            offscreenPageLimit = 4
        }

        tl_main_act_bottom_bar.setupWithViewPager(vp_main_act_pager, true)

        val tabLayout: View = this.layoutInflater.inflate(R.layout.tab_main_activity, null, false)
        tl_main_act_bottom_bar.getTabAt(0)!!.customView = tabLayout.findViewById(R.id.btn_tab_main_act_home)
        tl_main_act_bottom_bar.getTabAt(1)!!.customView = tabLayout.findViewById(R.id.btn_tab_main_act_map)
        tl_main_act_bottom_bar.getTabAt(2)!!.customView = tabLayout.findViewById(R.id.btn_tab_main_act_search)
        tl_main_act_bottom_bar.getTabAt(3)!!.customView = tabLayout.findViewById(R.id.btn_tab_main_act_badge)
    }
}