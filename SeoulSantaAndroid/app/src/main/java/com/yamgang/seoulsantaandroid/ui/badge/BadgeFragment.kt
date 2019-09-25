package com.yamgang.seoulsantaandroid.ui.badge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.yamgang.seoulsantaandroid.R
import kotlinx.android.synthetic.main.fragment_badge.view.*

class BadgeFragment : Fragment() {
    lateinit var inflater: LayoutInflater

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.inflater = inflater
        var view: View = inflater.inflate(R.layout.fragment_badge, container, false)
        viewInit(view)
        return view
    }

    private fun viewInit(view:View) {
        view.vp_frag_badge.offscreenPageLimit = 2
        view.vp_frag_badge.adapter = BadgeFragmentStatePagerAdapter(childFragmentManager,2)
        view.indicator_frag_badge.setViewPager(view.vp_frag_badge)
    }
}
