package com.example.sampleapp.APIServices

import retrofit2.CallAdapter
import okhttp3.Call
import retrofit2.Retrofit

object ApiFunctions {
    private var ourInstance:Retrofit? = null
    val instances:Retrofit
        get() {
            if(ourInstance == null)
                ourInstance = Retrofit.Builder()
                    .baseUrl("https://reqres.in/api/")
                    .build()
                return ourInstance!!
        }
}