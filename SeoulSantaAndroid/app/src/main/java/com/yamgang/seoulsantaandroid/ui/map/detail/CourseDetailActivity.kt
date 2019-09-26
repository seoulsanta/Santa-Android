package com.yamgang.seoulsantaandroid.ui.map.detail

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.model.get.GetCourse
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)

        networkService = ApplicationController.networkService

        val course_idx = intent.getIntExtra("course_idx",-1)
        course(course_idx)


        getKeyhash()

        btn_start_course.setOnClickListener {
            val intent = Intent(applicationContext,CourseMapActivity::class.java)
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
//                    Glide.with(this@CourseDetailActivity)
//                        .load(response.body()!!.data.mountain_img)
//                        .into(img_mt_present)
//                    Glide.with(this@CourseDetailActivity)
//                        .load(response.body()!!.data.course_img)
//                        .into(img_course)
                    mt_name.text = response.body()!!.data.mountain_name
                    mt_content.text = response.body()!!.data.mountain_content
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
}
