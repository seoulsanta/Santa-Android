package com.yamgang.seoulsantaandroid.ui.search

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.model.get.GetMountainRecommend
import com.yamgang.seoulsantaandroid.model.get.GetSearchMountain
import com.yamgang.seoulsantaandroid.model.get.SearchMountainCourseData
import com.yamgang.seoulsantaandroid.model.get.SearchMountainData
import com.yamgang.seoulsantaandroid.ui.MainActivity
import com.yamgang.seoulsantaandroid.ui.search.adapter.SearchResultCourseRVAdapter
import com.yamgang.seoulsantaandroid.util.ApplicationController
import com.yamgang.seoulsantaandroid.util.NetworkService
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.backgroundColor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class SearchFragment : Fragment() {

    lateinit var networkService: NetworkService
    var courseList: ArrayList<SearchMountainCourseData> = ArrayList()

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
        networkService = ApplicationController.networkService
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getMountainRecommend()

        view_search.isSelected = true
        view_search_detail.isSelected = true

//        setVisible(search_container)
//        setGone(search_detail_container)

        btn_search_icon.setOnClickListener {
            var search_word: String = edt_search_mountain.text.toString()
            if (search_word.length < 2) {
                Toast.makeText(this@SearchFragment.context!!, "두 글자 이상 입력해주세요 :)", Toast.LENGTH_SHORT).show()
            } else {
                view_search_detail.isSelected = true
                getSearchResponse(search_word)
            }
        }

        btn_search_recommend1.setOnClickListener {
            var search_word: String = tv_search_recommend1.text.toString()
            getSearchResponse(search_word)
        }

        btn_search_recommend2.setOnClickListener {
            var search_word: String = tv_search_recommend2.text.toString()
            getSearchResponse(search_word)
        }

        btn_search_recommend3.setOnClickListener {
            var search_word: String = tv_search_recommend3.text.toString()
            getSearchResponse(search_word)
        }

        btn_search_detail.setOnClickListener {
            var search_word: String = edt_search_detail_word.text.toString()
            setGone(btn_search_detail)
            setVisible(btn_search_x)

            if (search_word.length < 2) {
                Toast.makeText(this@SearchFragment.context!!, "두 글자 이상 입력해주세요 :)", Toast.LENGTH_SHORT).show()
            } else {
                view_search_detail.isSelected = true
                getSearchResponse(search_word)
            }
            //refreshFragment()
        }

        btn_search_x.setOnClickListener {
            edt_search_detail_word.setText("")
            view_search_detail.isSelected = false
            setGone(btn_search_x)
            setVisible(btn_search_detail)
            try {
                // 키보드 포커스 주기
                edt_search_detail_word.requestFocus()
                var imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(edt_search_mountain, 0)
            } catch (e: java.lang.Exception) {
            }

        }

        edt_search_detail_word.setOnClickListener {
            setGone(btn_search_x)
            setVisible(btn_search_detail)
        }

        edt_search_detail_word.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().length < 2) {
                    view_search_detail.isSelected = false
                    view_search.backgroundColor = Color.GRAY
                } else {
                    view_search_detail.isSelected = true
                    view_search.setBackgroundColor(getResources().getColor(R.color.colorMain))
                }
            }
        })


        edt_search_mountain.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().length < 2) {
                    view_search.isSelected = false
                    view_search.backgroundColor = Color.GRAY
                } else {
                    view_search.isSelected = true
                    view_search.setBackgroundColor(getResources().getColor(R.color.colorMain))
                }
            }
        })
    }

    // 산 검색 서버 통신
    fun getSearchResponse(search_word: String) {
        val getSearchMountainResponse = networkService.getSearchMountain("application/json", search_word)
        getSearchMountainResponse.enqueue(object : Callback<GetSearchMountain> {
            override fun onFailure(call: Call<GetSearchMountain>, t: Throwable) {
                Log.e("getSearchResponse", t.toString())
            }

            override fun onResponse(call: Call<GetSearchMountain>, response: Response<GetSearchMountain>) {
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        edt_search_detail_word.setText(search_word)
                        Log.v("현주", "통신 성공")
                        getResults()
                        response.body()?.let {
                            var responseData: SearchMountainData = response.body()!!.data
                            checkMountainLocation(responseData.mountain_idx)
                            tv_search_result_mt_name.setText(responseData.mountain_name)
                            tv_search_result_mt_name_balloon.setText(responseData.mountain_name)
                            var mountain_altitude: String = addCommatoNum(responseData.mountain_altitude)
                            tv_search_result_length.setText(mountain_altitude)

                            courseList = responseData.course
                            var courseNum: String = "추천코스 " + courseList.size.toString() + "개"
                            tv_search_result_course_num.setText(courseNum)

                            setRecyclerView(courseList)
                        }
                    }
                }

                // 검색 결과 없을 때
                response.errorBody()?.let {
                    val type: Type = object : TypeToken<GetMountainRecommend>() {}.type
                    val gson: Gson = GsonBuilder().create()
                    val responseJson: GetMountainRecommend = gson.fromJson(it.string().toString(), type)

                    if (response.code() == 404) {
                        if (responseJson.message == "Not Found.") {
                            edt_search_detail_word.setText(search_word)
                            getNoResult()
                        }
                    }
                }
            }
        })
    }

    // 추천 검색어 서버 통신
    fun getMountainRecommend() {
        val getMountainRecommend = networkService.getMountainRecommend("application/json")
        getMountainRecommend.enqueue(object : Callback<GetMountainRecommend> {
            override fun onFailure(call: Call<GetMountainRecommend>, t: Throwable) {
                Log.e("getMountainRecommend", t.toString())
            }

            override fun onResponse(call: Call<GetMountainRecommend>, response: Response<GetMountainRecommend>) {
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        response.body()?.let {
                            val dataList: ArrayList<String> = response.body()!!.data
                            tv_search_recommend1.setText(dataList[0])
                            tv_search_recommend2.setText(dataList[1])
                            tv_search_recommend3.setText(dataList[2])
                        }
                    }
                }
            }
        })
    }

    // 고도 받아오는 거에 콤마 처리
    fun addCommatoNum(num: Int): String {
        var str_num: String = num.toString()
        var len_str_num: Int = str_num.length
        if (len_str_num >= 4) {
            str_num =
                str_num.substring(0, len_str_num - 3) + "," + str_num.substring(len_str_num - 3, len_str_num) + "m"
        } else {
            str_num = str_num + "m"
        }
        return str_num
    }

    // 검색 결과가 있을 때
    fun getResults() {
        if (search_detail_container.isGone) {
            setGone(search_container)
            setVisible(search_detail_container)
        }

        setVisible(search_result_container)
        setVisible(rv_search_result_course_list)
        setGone(search_result_no_item_container)
    }

    // 검색 결과가 없을 때
    fun getNoResult() {
        if (search_detail_container.isGone) {
            setGone(search_container)
            setVisible(search_detail_container)
        }

        setVisible(search_result_no_item_container)
        setGone(search_result_container)
        setGone(rv_search_result_course_list)
    }

    private fun setRecyclerView(tmp: ArrayList<SearchMountainCourseData>) {
        var searchResultCourseRVAdapter = SearchResultCourseRVAdapter(this@SearchFragment.context!!, tmp)
        rv_search_result_course_list!!.apply {
            adapter = searchResultCourseRVAdapter
            layoutManager = LinearLayoutManager(this@SearchFragment.context!!, LinearLayoutManager.HORIZONTAL, false)
        }
        searchResultCourseRVAdapter.notifyDataSetChanged()
    }

    // 산의 위치 조정하기
    fun controlMountainLocataion(marginTop: Int, marginEnd: Int) {
        val dpRatio = context!!.getResources().displayMetrics.density
        val marginTopDP = (marginTop * dpRatio).toInt()
        val marginEndDP = (marginEnd * dpRatio).toInt()

        val constraintSet = ConstraintSet()
        constraintSet.clone(cl_search_result)
        TransitionManager.beginDelayedTransition(cl_search_result)
        constraintSet.connect(
            R.id.iv_search_result_mt,
            ConstraintSet.END,
            R.id.iv_search_result_map,
            ConstraintSet.END,
            marginEndDP
        )
        constraintSet.connect(
            R.id.iv_search_result_mt,
            ConstraintSet.TOP,
            R.id.iv_search_result_map,
            ConstraintSet.TOP,
            marginTopDP
        )
        constraintSet.applyTo(cl_search_result)
    }

    // 어떤 산인지 확인하기
    fun checkMountainLocation(idx: Int) {
        when (idx) {
            1 -> controlMountainLocataion(5, 57)        //불암산
            2 -> controlMountainLocataion(-5, 70)       //수락산
            3 -> controlMountainLocataion(-8, 113)      //도봉산
            4 -> controlMountainLocataion(20, 137)      //북한산
            5 -> controlMountainLocataion(67, 142)      //북악산
            6 -> controlMountainLocataion(81, 158)      //인왕산
            7 -> controlMountainLocataion(94, 169)      //안산
            8 -> controlMountainLocataion(67, 196)      //백련산
            9 -> controlMountainLocataion(136, 214)     //봉제산
            10 -> controlMountainLocataion(217, 162)    //관악산
            11 -> controlMountainLocataion(193, 115)    //우면산
            12 -> controlMountainLocataion(203, 82)     //구룡산
            13 -> controlMountainLocataion(112, 134)    //남산
            14 -> controlMountainLocataion(113, 55)     //아차산
        }
    }

    private fun refreshFragment() {
        var ft: FragmentTransaction = fragmentManager!!.beginTransaction()
        ft.detach(this).attach(this).commit()
    }

    private fun setGone(view: View) {
        view.visibility = View.GONE
    }

    private fun setVisible(view: View) {
        view.visibility = View.VISIBLE
    }
}