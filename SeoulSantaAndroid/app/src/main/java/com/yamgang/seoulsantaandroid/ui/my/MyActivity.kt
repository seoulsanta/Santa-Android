package com.yamgang.seoulsantaandroid.ui.my

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yamgang.seoulsantaandroid.R
import com.yamgang.seoulsantaandroid.model.put.PutMypageEditResponse
import com.yamgang.seoulsantaandroid.model.get.GetMypageResponse
import com.yamgang.seoulsantaandroid.ui.MainActivity
import com.yamgang.seoulsantaandroid.ui.login.LoginActivity
import com.yamgang.seoulsantaandroid.util.ApplicationController
import com.yamgang.seoulsantaandroid.util.User
import kotlinx.android.synthetic.main.activity_my.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.lang.Exception

class MyActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST: Int = 1
    val networkService = ApplicationController.networkService
    companion object{
        var instance: MyActivity = MyActivity()
    }
    lateinit var img: String
    var input_profile_img: MultipartBody.Part? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)
        getMypageResponse()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        btn_act_my_profile_modify.setOnClickListener {
            btn_act_my_edit_profile.visibility = View.VISIBLE
            tv_act_my_name.visibility = View.GONE
            et_act_my_name_edit.visibility = View.VISIBLE
            btn_act_my_profile_modify_complete.visibility = View.VISIBLE
            btn_act_my_profile_modify.visibility = View.GONE
        }
        btn_act_my_edit_profile.setOnClickListener{
            openGallery()
        }

        btn_act_my_profile_modify_complete.setOnClickListener {
            val input_profile_name = RequestBody.create(MediaType.parse("text/plain"), et_act_my_name_edit.text.toString())
            tv_act_my_name.text = et_act_my_name_edit.text.toString()
            putMypageEditResponse(input_profile_name, input_profile_img)
            btn_act_my_edit_profile.visibility = View.GONE
            tv_act_my_name.visibility = View.VISIBLE
            et_act_my_name_edit.visibility = View.GONE
            btn_act_my_profile_modify_complete.visibility = View.GONE
            btn_act_my_profile_modify.visibility = View.VISIBLE
        }
        btn_act_my_logout.setOnClickListener {
            User.authorization = null
            User.refreshtoken = null
            startActivity<LoginActivity>()
            MainActivity.MainClass.act?.finish()
            finish()

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

    fun openGallery() {
        var intent = Intent(Intent.ACTION_GET_CONTENT)
        //intent.setType("image/*")
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun getMypageResponse(){
        val getMypageResponse = networkService.getMypageResponse("application/json", User.authorization)
        getMypageResponse.enqueue(object :Callback<GetMypageResponse> {
            override fun onFailure(call: Call<GetMypageResponse>, t: Throwable) {
                Log.e("MyActivity-checkfail", t.toString())
            }

            override fun onResponse(call: Call<GetMypageResponse>, response: Response<GetMypageResponse>) {
                if(response.isSuccessful) {
                    try{
                        var option: RequestOptions = RequestOptions().circleCrop()
                        Glide.with(this@MyActivity)
                            .load(response.body()!!.data.img)
                            .apply(option)
                            .into(iv_act_my_profile)
                        tv_act_my_name.text = response.body()!!.data.name
                        tv_act_my_version.text = "최신 "+"1.0.0"+" 사용중"

                    } catch (e:Exception) {}
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
                //data에서 절대경로로 이미지를 가져옴
                var selectedPictureUri: Uri = data.data!!
                val options = BitmapFactory.Options()
                val inputStream: InputStream? = contentResolver!!.openInputStream(selectedPictureUri)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
                val photoBody = RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray())

                input_profile_img = MultipartBody.Part.createFormData(
                    "img",
                    File(selectedPictureUri.toString()).name + ".jpg",
                    photoBody
                )

                Glide.with(this@MyActivity)
                    .load(selectedPictureUri)
                    .circleCrop()
                    .into(iv_act_my_profile)

            } else {
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "사진 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }

    }

    private fun putMypageEditResponse(name: RequestBody, img: MultipartBody.Part?) {
        val putMypageEditResponse = networkService.putMypageEditResponse(User.authorization, name, img)
        Log.e("MyActivity-editfail", "in")
        putMypageEditResponse.enqueue(object : Callback<PutMypageEditResponse> {
            override fun onFailure(call: Call<PutMypageEditResponse>, t: Throwable) {
                Log.e("MyActivity-editfail", t.toString())
            }

            override fun onResponse(call: Call<PutMypageEditResponse>, response: Response<PutMypageEditResponse>) {
                if(response.isSuccessful) {
                    try{
                        Log.e("MyActivity-editResponse", "success")
                    }catch (e: Exception) {
                        Log.e("MyActivity-editResponse", "fail")
                    }
                }
            }
        })
    }
}