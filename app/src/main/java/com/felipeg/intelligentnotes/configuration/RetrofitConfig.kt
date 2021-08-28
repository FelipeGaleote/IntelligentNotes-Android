
package com.felipeg.intelligentnotes.configuration

import com.felipeg.intelligentnotes.BuildConfig
import com.felipeg.intelligentnotes.annotation.data.AnnotationService
import com.felipeg.intelligentnotes.authentication.data.LoginService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {
    
    fun loginService() = makeBasicRetrofit().create(LoginService::class.java)

    fun annotationService(token: String) = makeRetrofitWithToken(token).create(AnnotationService::class.java)

    private fun makeRetrofitWithToken(token: String): Retrofit {
        val addHeaderInterceptor = Interceptor {
            val request = it.request().newBuilder().addHeader("Authorization", "Bearer $token").build()
            it.proceed(request)
        }

        val client = OkHttpClient().newBuilder().addInterceptor(addHeaderInterceptor).build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    private fun makeBasicRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

}