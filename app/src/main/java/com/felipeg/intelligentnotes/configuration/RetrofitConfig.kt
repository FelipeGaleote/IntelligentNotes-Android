package com.felipeg.intelligentnotes.configuration

import com.felipeg.intelligentnotes.data.LoginService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.50:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    fun loginService() = retrofit.create(LoginService::class.java)

}