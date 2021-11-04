
package com.felipeg.intelligentnotes.configuration

import com.felipeg.intelligentnotes.BuildConfig
import com.felipeg.intelligentnotes.annotation.data.AnnotationService
import com.felipeg.intelligentnotes.authentication.data.LoginService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {
    
    fun loginService() = makeBasicRetrofit().create(LoginService::class.java)

    fun annotationService(token: String) = makeRetrofitWithToken(token).create(AnnotationService::class.java)

    private fun makeRetrofitWithToken(token: String): Retrofit {

        val interceptors : MutableList<Interceptor> = mutableListOf()

        val addHeaderInterceptor = Interceptor {
            val request = it.request().newBuilder().addHeader("Authorization", "Bearer $token").build()
            it.proceed(request)
        }

        interceptors.add(addHeaderInterceptor)

        return makeRetrofitBuilder(interceptors = interceptors)
            .build()
    }

    private fun makeBasicRetrofit(): Retrofit {
        return makeRetrofitBuilder()
            .build()
    }

    private fun makeRetrofitBuilder(interceptors: List<Interceptor> = emptyList()): Retrofit.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient().newBuilder()
        httpClient.addInterceptor(logging)

        interceptors.forEach {
            httpClient.addInterceptor(it)
        }

        return Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
    }

}