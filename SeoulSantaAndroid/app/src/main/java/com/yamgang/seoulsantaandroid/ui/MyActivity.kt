package com.yamgang.seoulsantaandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yamgang.seoulsantaandroid.R
import kotlinx.android.synthetic.main.activity_my.*

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)
        viewInit()
        setOnClickListener()
    }

    private fun viewInit() {
        tv_act_my_name.text = "으밍"
        tv_act_my_id.text = "eumiing"
        tv_act_my_version.text = "최신 "+"1.0.0"+" 사용중"
    }

    private fun setOnClickListener() {
        btn_act_my_profile_modify.setOnClickListener {
            //프로필 수정
        }
        btn_act_my_login_info.setOnClickListener {
            //로그인 정보
        }
        btn_act_my_setting.setOnClickListener {
            //설정
        }
        btn_act_my_notice.setOnClickListener {
            //공지사항
        }
        btn_act_my_help.setOnClickListener {
            //도움말
        }
    }
}