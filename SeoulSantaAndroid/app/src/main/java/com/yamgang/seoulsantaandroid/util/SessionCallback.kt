package com.yamgang.seoulsantaandroid.util

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeResponseCallback
import com.kakao.usermgmt.response.model.UserProfile
import com.kakao.util.exception.KakaoException
import com.yamgang.seoulsantaandroid.model.post.PostKakaoLoginResponse
import com.yamgang.seoulsantaandroid.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SessionCallback (mContext: Context) : ISessionCallback {
    var mContext: Context = mContext
    val networkService =ApplicationController.networkService
    lateinit var accessToken: String
    //var jsonObject : JSONObject(this)

    //로그인 실패
    override fun onSessionOpenFailed(exception: KakaoException?) {
        Toast.makeText(mContext,"아예실패",Toast.LENGTH_SHORT).show()
        Log.e("SessionOpenFailed :: ","OnSessionClosed : ")

    }

    //로그인 성공
    override fun onSessionOpened() {
        requestMe()
    }

    fun requestMe(){
        val user: UserManagement = UserManagement.getInstance()
        user.requestMe(object : MeResponseCallback(){
            override fun onSuccess(result: UserProfile?) {
                if(result != null){
                    KakaoLoginResponse()
                }
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                Log.e("SessionCallback :: ","OnSessionClosed : " + errorResult!!.errorMessage)
            }

            override fun onNotSignedUp() {
                Log.e("SessionCallback :: ", "onNotSignedUp")
            }
        })
    }

    private fun KakaoLoginResponse(){
        val session = Session.getCurrentSession()
        accessToken = session.accessToken.toString()
        val token = session.accessToken
        Log.d("access_token", token.toString())
        val postKakaoLoginResponse = networkService.postKakaoLoginResponse("application/json",token)
        postKakaoLoginResponse.enqueue(object: Callback<PostKakaoLoginResponse> {
            override fun onFailure(call: Call<PostKakaoLoginResponse>, t: Throwable) {
                Log.e("*****KakaoLogin Failed",t.toString())
            }

            override fun onResponse(call: Call<PostKakaoLoginResponse>?, response: Response<PostKakaoLoginResponse>?) {
                if(response!!.isSuccessful){
                    val authorization = response.body()!!.data.authorization
                    val refreshtoken = response.body()!!.data.refreshtoken
                    User.authorization = authorization
                    User.refreshtoken = refreshtoken
                    Log.e("*****KakaoLogin Response","성공")

                    val intent = Intent(mContext,MainActivity::class.java)
                    mContext.startActivity(intent)
                } else {
                    Log.e("*****KakaoLogin Response","실패")
                }
            }
        })
    }
}