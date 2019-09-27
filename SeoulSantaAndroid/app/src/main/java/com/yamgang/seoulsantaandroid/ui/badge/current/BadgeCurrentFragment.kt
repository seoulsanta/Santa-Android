package com.yamgang.seoulsantaandroid.ui.badge.current

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.model.get.BadgeData
import com.yamgang.seoulsantaandroid.ui.badge.BadgeFragment
import kotlinx.android.synthetic.main.fragment_badge_current.view.*

class BadgeCurrentFragment : Fragment() {

    var course_name: String = "코스"
    var date: String="날짜"

    companion object{
        var instance: BadgeCurrentFragment = BadgeCurrentFragment()
    }
    lateinit var inflater: LayoutInflater
    //BadgeFragment에서 통신해서 넘어온 데이타리스트 사용하기
    val data: BadgeData = BadgeFragment.instance.data
    lateinit var badgeCurrentRecyclerViewAdapter: BadgeCurrentRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.inflater = inflater
        var view: View = inflater.inflate(R.layout.fragment_badge_current, container, false)
        setRecyclerView(view)
        return view
    }

    private fun setRecyclerView(view:View) {
        badgeCurrentRecyclerViewAdapter = BadgeCurrentRecyclerViewAdapter(activity!!, data.badge,data.total)
        view.rv_frag_badge_current_list.adapter = badgeCurrentRecyclerViewAdapter
        view.rv_frag_badge_current_list.layoutManager = GridLayoutManager(activity,3)
    }
}
