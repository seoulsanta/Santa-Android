package com.yamgang.seoulsantaandroid.ui.map.detail

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.yamgang.seoulsantaandroid.R
import kotlinx.android.synthetic.main.activity_course_detail.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class CourseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)

        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }

        btn_start_course.setOnClickListener {
            val intent = Intent(applicationContext,CourseMapActivity::class.java)
            startActivity(intent)
        }

    }
}
