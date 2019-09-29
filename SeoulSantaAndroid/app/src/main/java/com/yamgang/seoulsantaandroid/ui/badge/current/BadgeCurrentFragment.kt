package com.yamgang.seoulsantaandroid.ui.badge.current

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.model.get.BadgeData
import com.yamgang.seoulsantaandroid.ui.MainActivity
import com.yamgang.seoulsantaandroid.ui.badge.BadgeFragment
import com.yamgang.seoulsantaandroid.util.User
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_badge_current.*
import kotlinx.android.synthetic.main.fragment_badge_current.view.*
import org.jetbrains.anko.support.v4.ctx

class BadgeCurrentFragment : Fragment() {

    var course_name: String = "코스"
    var date: String="날짜"
    companion object{
        var instance: BadgeCurrentFragment = BadgeCurrentFragment()
    }
    lateinit var badgeCurrentRecyclerViewAdapter: BadgeCurrentRecyclerViewAdapter
    var data :BadgeData = BadgeFragment.instance.data

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_badge_current, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        badgeCurrentRecyclerViewAdapter = BadgeCurrentRecyclerViewAdapter(activity!!, data.badge, data.total)
        rv_frag_badge_current_list.adapter = badgeCurrentRecyclerViewAdapter
        rv_frag_badge_current_list.layoutManager = GridLayoutManager(activity,3)
    }

    fun refresh() {
        badgeCurrentRecyclerViewAdapter.notifyDataSetChanged()
        setRecyclerView()
    }
}
