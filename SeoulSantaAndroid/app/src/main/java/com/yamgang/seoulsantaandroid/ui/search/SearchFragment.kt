package com.yamgang.seoulsantaandroid.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.model.MountainCourses
import com.yamgang.seoulsantaandroid.ui.MainActivity
import com.yamgang.seoulsantaandroid.ui.search.adapter.SearchResultCourseRVAdapter
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    var onBackPressedListener = object : OnBackPressedListener {
        override fun onBackPressed() {
            if (search_container.isGone) {
                setGone(search_detail_container)
                setVisible(search_container)
            } else {
                activity!!.finish()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        assert(activity != null)

        (activity as MainActivity).setOnBackPressedListener(onBackPressedListener)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_search, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setVisible(search_container)
        setGone(search_detail_container)

        btn_search_icon.setOnClickListener {
            var search_word :String = edt_search_mountain.text.toString()
            edt_search_detail_word.setText(search_word)
            setGone(search_container)
            setVisible(search_detail_container)

            // 결과가 있을 때
            //getResults()

            //결과가 없을 때
            getNoResult()
        }

        btn_search_x.setOnClickListener {
            edt_search_detail_word.setText(" ")
            try {
                // 키보드 포커스 주기
                edt_search_detail_word.requestFocus()
                var imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(edt_search_mountain, 0)
            } catch (e: java.lang.Exception) {
            }
            setGone(btn_search_x)
            setVisible(btn_search_detail)
        }
    }

    fun getResults(){
        setGone(search_result_no_item_container)
        setRecyclerView()
    }

    fun getNoResult() {
        setGone(search_result_container)
        setGone(rv_search_result_course_list)
    }

    fun setRecyclerView() {
        var tmp: ArrayList<MountainCourses> = arrayListOf(
            MountainCourses(
                "혜진현무",
                "https://hcom-graph.s3.ap-northeast-2.amazonaws.com/mbll_2019-08-16_143830.png"
            ),
            MountainCourses("초보자용 코스", "https://hcom-graph.s3.ap-northeast-2.amazonaws.com/mbll_2019-08-16_143830.png")
            ,
            MountainCourses("고급자용 코스", "https://hcom-graph.s3.ap-northeast-2.amazonaws.com/mbll_2019-08-16_143830.png")
            ,
            MountainCourses("오쪼고 코스", "https://hcom-graph.s3.ap-northeast-2.amazonaws.com/mbll_2019-08-16_143830.png")
        )
        try {
            rv_search_result_course_list!!.apply {
                adapter = SearchResultCourseRVAdapter(this@SearchFragment.context!!, tmp)
                layoutManager =
                    LinearLayoutManager(this@SearchFragment.context!!, LinearLayoutManager.HORIZONTAL, false)
            }
        } catch (e: Exception) {
            Log.e("현주 에러", "" + e.message)
        }
    }

    private fun setGone(view: View) {
        view.visibility = View.GONE
    }

    private fun setInvisible(view: View) {
        view.visibility = View.INVISIBLE
    }

    private fun setVisible(view: View) {
        view.visibility = View.VISIBLE
    }
}