package com.felipeg.intelligentnotes.authentication.data

import com.felipeg.intelligentnotes.authentication.data.model.LoggedInUser
import com.felipeg.intelligentnotes.common.Result

class LoginRepository(val dataSource: LoginDataSource) {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        return dataSource.login(username, password)
    }
}