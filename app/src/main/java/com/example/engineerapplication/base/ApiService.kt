package com.example.engineerapplication.base


import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("upload-image.php")
    fun uploadImage(
        @Field("name") name: String,
        @Field("image") image: String
    ): Call<UploadImageResponse>

}
