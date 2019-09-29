package com.yamgang.seoulsantaandroid.ui.badge.finish

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.model.get.BadgeListData
import com.yamgang.seoulsantaandroid.ui.map.detail.CourseMapActivity

class BadgeFinishRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<BadgeListData>): RecyclerView.Adapter<BadgeFinishRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_badge_finish,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = dataList[position].course_name
        val date_split = dataList[position].date
        holder.date.text=date_split.substring(0,4)+"."+date_split.substring(5,7)+"."+date_split.substring(8,10)+"  "+date_split.substring(11,13)+":"+date_split.substring(14,16)
        holder.view.setOnClickListener {
            val intent = Intent(ctx, CourseMapActivity::class.java)
            intent.putExtra("course_idx",dataList[position].course_idx)
            intent.putExtra("name",dataList[position].course_name)
            ctx.startActivity(intent)
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val view: RelativeLayout = itemView.findViewById(R.id.rl_rv_item_badge_finish) as RelativeLayout
        val name: TextView = itemView.findViewById(R.id.tv_rv_item_badge_finish_title) as TextView
        val date: TextView = itemView.findViewById(R.id.tv_rv_item_badge_finish_time) as TextView
    }
}