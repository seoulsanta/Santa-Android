package com.yamgang.seoulsantaandroid.ui.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.auth.AuthType
import com.kakao.auth.Session
import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.util.SessionCallback
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setOnClickListener()

        btn_login_naver.setOnClickListener {
            Toast.makeText(this, "서비스 준비 중입니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setOnClickListener() {
        btn_login_kakao.setOnClickListener {
            val session = Session.getCurrentSession()
            Log.e("Tag","kakaoLogin")
            session.addCallback(SessionCallback(this))
            session.open(AuthType.KAKAO_ACCOUNT,this)
        }
    }
}
