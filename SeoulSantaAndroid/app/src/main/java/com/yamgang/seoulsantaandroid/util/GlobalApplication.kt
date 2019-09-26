package com.yamgang.seoulsantaandroid.util

import android.app.Application
import com.kakao.auth.KakaoSDK
import android.app.Activity
import android.util.Log


open class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        // KakaoSDK 초기화
        KakaoSDK.init(KaKaoSDKAdapter())
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    companion object {
        private var instance: GlobalApplication? = null
        private val currentActivity: Activity? = null
        fun getCurrentActivity(): Activity {
            Log.e(
                "TAG",
                "++ currentActivity : " + currentActivity?.javaClass?.simpleName
            )
            return currentActivity!!
        }
        val globalApplicationContext: GlobalApplication
            get() {
                if(instance == null) {
                    throw IllegalAccessException("This Application does not inherit com.kakao.GlobalApplication")
                }
                return instance as GlobalApplication
            }
    }
}