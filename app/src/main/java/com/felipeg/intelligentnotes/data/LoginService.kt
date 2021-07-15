package com.felipeg.intelligentnotes.data

import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("user/login")
    fun login(@Body loginInput: LoginInput) : Deferred<Response<LoginOutput>>

    data class LoginInput(val username: String, val password: String)
    data class LoginOutput(var id: String, var username: String, var email: String)
}