package com.felipeg.intelligentnotes.authentication.data

import com.felipeg.intelligentnotes.configuration.RetrofitConfig
import com.felipeg.intelligentnotes.authentication.data.LoginService.LoginInput
import com.felipeg.intelligentnotes.authentication.data.model.LoggedInUser
import com.felipeg.intelligentnotes.common.Result
import java.io.IOException

class LoginDataSource {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            val requestBody = LoginInput(username, password)

            with(RetrofitConfig().loginService().login(requestBody).await()) {
                if (isSuccessful) {
                    val token = headers().get("Authorization")
                    body()?.let {
                        val fakeUser = LoggedInUser(it.id, it.username, token!!)
                        return Result.Success(fakeUser)
                    }
                }
            }
            return Result.Error(Exception("Failed to login"))
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }
}