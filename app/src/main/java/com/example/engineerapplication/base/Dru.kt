package com.example.engineerapplication.base


import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.IOException

object Dru {

    private fun getImageToString(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imgByte = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(imgByte, Base64.DEFAULT)
    }

    fun uploadImage(
        context: Context,
        baseUrl: String,
        imageName: String,
        uri: Uri?,
        listener: (UploadImageResponse?) -> Unit,
    ) {
        var bitmap: Bitmap? = null
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (bitmap == null) return
        val imageFile = getImageToString(bitmap)
        val call = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
            .uploadImage(imageName, imageFile)
        call.enqueue(object : Callback<UploadImageResponse> {
            override fun onResponse(
                call: Call<UploadImageResponse>,
                response: Response<UploadImageResponse>
            ) {
                Log.d(TAG, "onResponse: ")
                if (!response.isSuccessful) return
                listener(response.body())
            }

            override fun onFailure(call: Call<UploadImageResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun ImageView.loadImageCircle(url: String?) {
        Glide.with(this)
            .load(url)
//            .circleCrop()
            .into(this)
    }

    private const val TAG = "DruV2"

}
