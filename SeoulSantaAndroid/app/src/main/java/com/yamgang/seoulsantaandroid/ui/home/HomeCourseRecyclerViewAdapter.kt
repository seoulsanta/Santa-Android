package com.yamgang.seoulsantaandroid.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yamgang.seoulsantaandroid.R

class HomeCourseRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<HomeCourseData>): RecyclerView.Adapter<HomeCourseRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_home_course,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = dataList[position].mountain_name
        Glide.with(ctx)
            .load(dataList[position].mountain_img)
            .into(holder.img)
        holder.full.setOnClickListener {
            //코스띄우기
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val full: RelativeLayout = itemView.findViewById(R.id.rl_rv_item_home_course) as RelativeLayout
        val name: TextView = itemView.findViewById(R.id.tv_rv_item_home_mountain_name) as TextView
        val img: ImageView = itemView.findViewById(R.id.iv_rv_item_home_mountain_img) as ImageView
    }
}