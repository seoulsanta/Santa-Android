package com.yamgang.seoulsantaandroid.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
