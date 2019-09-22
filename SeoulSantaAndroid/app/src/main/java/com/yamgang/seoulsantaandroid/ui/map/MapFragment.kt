package com.yamgang.seoulsantaandroid.ui.map


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.yamgang.seoulsantaandroid.R
import kotlinx.android.synthetic.main.fragment_map.view.*

class MapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_map, container, false)
        clickEvent(v)

        return v
    }

    fun clickEvent(v:View){
        v.mt_1.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

        }
        v.mt_2.setOnClickListener {
            v.big_mountain.visibility = View.INVISIBLE
            v.tv_click_mt.visibility = View.INVISIBLE
            v.rcv_map.visibility = View.VISIBLE

        }
        //요런식으로 산 15개(?) 까지..

    }

}
