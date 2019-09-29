package com.yamgang.seoulsantaandroid.ui.search.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.model.get.SearchMountainCourseData
import com.yamgang.seoulsantaandroid.ui.map.detail.CourseDetailActivity

class SearchResultCourseRVAdapter(private val ctx: Context, var dataList: ArrayList<SearchMountainCourseData>) :
    RecyclerView.Adapter<SearchResultCourseRVAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_search_mountain_course, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var options: RequestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(10))

        dataList[position].let { item ->
            Glide.with(ctx)
                .load(item.course_img)
                .apply(options)
                .into(holder.ivCourseImage)

            holder.tvCourseName.text = item.theme_name

            // MapDetail으로 이동하기
            holder.itemView.setOnClickListener {
                val intent = Intent(ctx, CourseDetailActivity::class.java)
                intent.putExtra("course_idx",item.course_idx)
                ctx.startActivity(intent)
            }
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCourseImage: ImageView = itemView.findViewById(R.id.iv_rv_search_result_course)
        val tvCourseName: TextView = itemView.findViewById(R.id.tv_rv_search_result_course)
    }
}