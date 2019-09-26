package com.yamgang.seoulsantaandroid.ui.badge

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yamgang.seoulsantaandroid.ui.badge.current.BadgeCurrentFragment
import com.yamgang.seoulsantaandroid.ui.badge.finish.BadgeFinishFragment

class BadgeFragmentStatePagerAdapter(fm: FragmentManager, private val fragmentCount: Int):FragmentStatePagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return BadgeCurrentFragment()
            1 -> return BadgeFinishFragment()
            else -> return BadgeCurrentFragment()
        }
    }
    override fun getCount(): Int = fragmentCount
}