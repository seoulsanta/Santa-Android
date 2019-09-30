package com.yamgang.seoulsantaandroid.ui.map

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.yamgang.seoulsantaandroid.R
import kotlinx.android.synthetic.main.dialog_safety_tips.*

class SafetyTipsDialog(context: Context) : Dialog(context)  {

    var isChecked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_safety_tips)

        setGone(btn_dialog_safety_selected)
        setVisible(btn_dialog_safety_unselected)

        btn_dialog_safety_agree.setOnClickListener{
            if (isChecked == true){
                setGone(iv_dialog_safety_check)
                setGone(btn_dialog_safety_selected)
                setVisible(btn_dialog_safety_unselected)
            }else{
                setVisible(iv_dialog_safety_check)
                setVisible(btn_dialog_safety_selected)
                setGone(btn_dialog_safety_unselected)
            }
        }

        btn_dialog_safety_selected.setOnClickListener {
            dismiss()
        }
    }


    private fun setGone(view: View) {
        view.visibility = View.GONE
    }

    private fun setVisible(view: View) {
        view.visibility = View.VISIBLE
    }
}
//        sv_dialog_safety_tips.setScrollViewListener(ScrollViewListener{
//            override fun onScrollChanged(scrollView: CustomScrollView, x: Int, y: Int, oldx: Int, oldy: Int) {
//                Log.v("현주", "되고 잇는 중1")
//                val view: View = scrollView.getChildAt(scrollView.getChildCount() - 1);
//                var diff : Int = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()))
//                if (diff == 0 ) { // 스크롤 bottom
//                    setGone(btn_dialog_safety_unselected)
//                    setVisible(btn_dialog_safety_selected)
//                }
//            }
//        })

//
//
//    private fun scroll() {
//        val textTotalHeight = cl_dialog_safety_contents.height
//        val pageHeight = sv_dialog_safety_tips.getHeight()
//        val scrollY = sv_dialog_safety_tips.getScrollY()
//        if (scrollY < textTotalHeight - pageHeight) {// not touch the bottom
//            sv_dialog_safety_tips.smoothScrollTo(0, scrollY + pageHeight)// scroll one page height
//        } else {// touch the bottom, dismiss the dialog
//            setGone(btn_dialog_safety_unselected)
//            setVisible(btn_dialog_safety_selected)
//        }
//    }

