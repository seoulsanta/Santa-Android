package com.yamgang.seoulsantaandroid.ui.map.detail

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.model.get.GetCourse
import com.yamgang.seoulsantaandroid.ui.map.SafetyTipsDialog
import com.yamgang.seoulsantaandroid.util.ApplicationController
import com.yamgang.seoulsantaandroid.util.NetworkService
import kotlinx.android.synthetic.main.activity_course_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class CourseDetailActivity : AppCompatActivity() {
    lateinit var networkService: NetworkService
    var name = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail) //투명상태바

        setStatusBarTransparent()

        networkService = ApplicationController.networkService

        val course_idx = intent.getIntExtra("course_idx",-1)
        course(course_idx)


        getKeyhash()

        btn_start_course.setOnClickListener {
            var safetyTipsDialog = SafetyTipsDialog(this)
            safetyTipsDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            safetyTipsDialog.setCanceledOnTouchOutside(false)
            safetyTipsDialog.show()

            val intent = Intent(applicationContext,CourseMapActivity::class.java)
            intent.putExtra("course_idx",course_idx)
            intent.putExtra("name",name)
            intent.putExtra("badge",0)
            startActivity(intent)
        }
    }

    fun course(course_idx:Int){
        val getCourse = networkService.getCourse("application/json",course_idx)
        getCourse.enqueue(object : Callback<GetCourse>{
            override fun onFailure(call: Call<GetCourse>, t: Throwable) {


            }

            override fun onResponse(call: Call<GetCourse>, response: Response<GetCourse>) {
                if(response.isSuccessful){
                    val drawable = this@CourseDetailActivity.getDrawable(R.drawable.rounding) as GradientDrawable
                    img_course.background = drawable
                    img_course.clipToOutline = true

                    Glide.with(this@CourseDetailActivity)
                        .load(response.body()!!.data.mountain_img)
                        .into(img_mt_present)
                    Glide.with(this@CourseDetailActivity)
                        .load(response.body()!!.data.course_img)
                        .centerCrop()
                        .into(img_course)

                    mt_name.text = response.body()!!.data.mountain_name
                    mt_content.text = response.body()!!.data.mountain_content
                    name = response.body()!!.data.course_name
                    course_name.text = response.body()!!.data.course_name
                    course_time.text = response.body()!!.data.time
                    course_degree.text = response.body()!!.data.degree

                }else{

                }
            }

        })
    }

    fun getKeyhash(){

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
    }

    private fun setStatusBarTransparent() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        if (Build.VERSION.SDK_INT >= 21) {
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
            return
        }
        winParams.flags = winParams.flags and bits.inv()
        win.attributes = winParams
    }
}
