package com.example.sampleapp.APIServices

import android.database.Observable
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.*

interface apis {
    @Headers("Content-Type: application/json")
    @POST("register")
    @FormUrlEncoded
    fun register(
        @Field("email") email:String,
        @Field("password") password:String):retrofit2.Call<ResponseBody>

    @POST("login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ):retrofit2.Call<ResponseBody>

    @GET("users")
    fun users():retrofit2.Call<ResponseBody>

}
