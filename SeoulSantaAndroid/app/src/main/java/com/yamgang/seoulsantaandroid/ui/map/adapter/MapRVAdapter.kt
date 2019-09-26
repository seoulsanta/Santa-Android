package com.yamgang.seoulsantaandroid.ui.map.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.model.Courses
import com.yamgang.seoulsantaandroid.model.Mountain
import com.yamgang.seoulsantaandroid.ui.map.detail.CourseDetailActivity
import com.yamgang.seoulsantaandroid.ui.search.adapter.SearchResultCourseRVAdapter

class MapRVAdapter(private val ctx: Context, var dataList: ArrayList<Courses>)
    : RecyclerView.Adapter<MapRVAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.item_rcv_map, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.courseName.text = dataList[position].course_name
        holder.courseTime.text = dataList[position].time
        holder.courseDegree.text = dataList[position].degree
//        Glide.with(ctx)
//            .load(dataList[position].course_img)
//            .into(holder.courseImg)

        holder.itemView.setOnClickListener {
            val intent = Intent(ctx,CourseDetailActivity::class.java)
            intent.putExtra("course_idx",dataList[position].course_idx)
            ctx.startActivity(intent)
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val courseName : TextView =itemView.findViewById(R.id.rcv_course_name)
        val courseImg : ImageView = itemView.findViewById(R.id.rcv_img_course)
        val courseTime : TextView = itemView.findViewById(R.id.rcv_course_time)
        val courseDegree : TextView = itemView.findViewById(R.id.rcv_course_degree)

    }
}