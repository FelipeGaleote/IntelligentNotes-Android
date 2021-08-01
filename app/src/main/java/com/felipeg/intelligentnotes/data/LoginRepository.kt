package com.felipeg.intelligentnotes.data

import android.content.Context
import com.felipeg.intelligentnotes.R
import com.felipeg.intelligentnotes.data.model.LoggedInUser

class LoginRepository(val dataSource: LoginDataSource, private val context: Context) {

    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        val result = dataSource.login(username, password)
        with(result) {
            if (this is Result.Success) {
                setLoggedInUser(data)
                saveTokenOnSharedPreferences(data.jwtToken)
            }
        }
        return result
    }

    private fun saveTokenOnSharedPreferences(jwtToken: String) {
        with(context) {
            val sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
            )
            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putString(getString(R.string.jwt_token_key), jwtToken)
            sharedPrefEditor.commit()
        }
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}