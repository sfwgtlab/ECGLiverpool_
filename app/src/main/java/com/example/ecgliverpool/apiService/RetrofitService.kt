package com.example.ecgliverpool.apiService

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService(){


    fun getProducts(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://shoppapp.liverpool.com.mx/appclienteservices/services/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}