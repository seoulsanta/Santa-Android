package com.yamgang.seoulsantaandroid.ui.badge.finish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.ui.badge.BadgeData
import com.yamgang.seoulsantaandroid.ui.badge.BadgeFragment
import com.yamgang.seoulsantaandroid.ui.badge.BadgeListData
import kotlinx.android.synthetic.main.fragment_badge_finish.view.*

class BadgeFinishFragment : Fragment() {

    lateinit var inflater: LayoutInflater
    //BadgeFragment에서 통신해서 넘어온 데이타리스트 사용하기
    val data: BadgeData = BadgeFragment.instance.data
    lateinit var badgeFinishRecyclerViewAdapter: BadgeFinishRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.inflater = inflater
        var view: View = inflater.inflate(R.layout.fragment_badge_finish, container, false)
        setRecyclerView(view)
        return view
    }

    private fun setRecyclerView(view:View) {
        if(data.badge.size == 0) {
            view.rl_frag_badge_finish_default_page.visibility = View.VISIBLE
            view.rl_frag_badge_finish_exist_page.visibility = View.GONE
        } else{
            view.rl_frag_badge_finish_default_page.visibility = View.GONE
            view.rl_frag_badge_finish_exist_page.visibility = View.VISIBLE
            badgeFinishRecyclerViewAdapter = BadgeFinishRecyclerViewAdapter(activity!!, data.badge)
            view.rv_frag_badge_finish_list.adapter = badgeFinishRecyclerViewAdapter
            view.rv_frag_badge_finish_list.layoutManager = LinearLayoutManager(activity)
        }
    }
}
