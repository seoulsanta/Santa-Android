package com.yamgang.seoulsantaandroid.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yamgang.seoulsantaandroid.ui.badge.BadgeFragment
import com.yamgang.seoulsantaandroid.ui.home.HomeFragment
import com.yamgang.seoulsantaandroid.ui.map.MapFragment
import com.yamgang.seoulsantaandroid.ui.search.SearchFragment

class MainTabFragmentStatePagerAdapter(fm: FragmentManager, private val fragmentCount: Int) :
    FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return HomeFragment()
            1 -> return MapFragment()
            2 -> return SearchFragment()
            3 -> return BadgeFragment()
            else -> return HomeFragment()
        }
    }

    override fun getCount(): Int = fragmentCount

}