package com.yamgang.seoulsantaandroid.ui.badge.current


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.yamgang.seoulsantaandroid.R
import kotlinx.android.synthetic.main.fragment_badge_current_dialog.view.*

class BadgeCurrentDialogFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View =  inflater.inflate(R.layout.fragment_badge_current_dialog, container, false)
        view.tv_frag_badge_current_dialog_date.text = BadgeCurrentFragment.instance.date
        view.tv_frag_badge_current_dialog_name.text = BadgeCurrentFragment.instance.course_name+"를 완주하였습니다!!"
        view.btn_frag_badge_current_dialog_close.setOnClickListener {
            dismiss()
        }
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return view
    }
}
