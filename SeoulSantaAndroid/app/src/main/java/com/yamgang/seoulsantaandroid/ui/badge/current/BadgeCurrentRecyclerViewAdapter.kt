package com.yamgang.seoulsantaandroid.ui.badge.current

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.ui.MainActivity
import com.yamgang.seoulsantaandroid.model.get.BadgeListData
import com.yamgang.seoulsantaandroid.ui.badge.BadgeFragment

class BadgeCurrentRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<BadgeListData>, var total: Int): RecyclerView.Adapter<BadgeCurrentRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_badge_current, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = total

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if(position < dataList.size){
            var img : Int = -1
            if(dataList[position].degree == "어려움"){
                img = R.drawable.badge_get_hard
            } else if(dataList[position].degree == "보통") {
                img = R.drawable.badge_get_normal
            } else if(dataList[position].degree == "쉬움") {
                img = R.drawable.badge_get_easy
            }
            Glide.with(ctx)
                .load(img)
                .into(holder.img)
            //뱃지 클릭할 시,
            val date_split = dataList[position].date
            holder.img.setOnClickListener {
                val dialog = BadgeCurrentDialogFragment()
                dialog.show((ctx as MainActivity).supportFragmentManager,dialog.tag)
                BadgeCurrentFragment.instance.course_name = dataList[position].course_name
                BadgeCurrentFragment.instance.date = date_split.substring(0,4)+"년 "+date_split.substring(5,7)+"월 "+date_split.substring(8,10)+"일"
                Log.e("BadgeCurrentRV", "Badge")
            }
        } else{
            Glide.with(ctx)
                .load(R.drawable.badge_default)
                .into(holder.img)
            Log.e("BadgeCurrentRV", "BadgeIn")

        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.iv_rv_item_badge_current_img) as ImageView
    }
}
