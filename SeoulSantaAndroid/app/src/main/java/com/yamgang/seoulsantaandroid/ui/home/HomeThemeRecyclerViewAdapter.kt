package com.yamgang.seoulsantaandroid.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yamgang.seoulsantaandroid.R

class HomeThemeRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<HomeThemeData>): RecyclerView.Adapter<HomeThemeRecyclerViewAdapter.Holder>() {

    lateinit var homeCourseRecyclerViewAdapter: HomeCourseRecyclerViewAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_home_theme,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = dataList[position].theme_name
        homeCourseRecyclerViewAdapter =
            HomeCourseRecyclerViewAdapter(ctx, dataList[position].course)
        holder.list.adapter = homeCourseRecyclerViewAdapter
        holder.list.layoutManager = LinearLayoutManager(ctx, LinearLayout.HORIZONTAL, false)
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_rv_item_home_theme_name) as TextView
        val list: RecyclerView = itemView.findViewById(R.id.rv_rv_item_home_course_list) as RecyclerView
    }
}