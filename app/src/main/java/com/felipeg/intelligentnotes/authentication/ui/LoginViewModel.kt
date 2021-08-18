package com.felipeg.intelligentnotes.authentication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felipeg.intelligentnotes.R
import com.felipeg.intelligentnotes.authentication.data.LoginRepository
import com.felipeg.intelligentnotes.common.Result
import com.felipeg.intelligentnotes.common.SharedPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val loginRepository: LoginRepository, private val sharedPreferencesRepository: SharedPreferencesRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = loginRepository.login(username, password)

            withContext(Dispatchers.Main) {
                if (result is Result.Success) {
                    saveTokenOnSharedPreferences(result.data.jwtToken)
                    _loginResult.value = LoginResult(success = LoggedInUserView(username = result.data.username))
                } else {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }
            }
        }
    }

    private fun saveTokenOnSharedPreferences(token: String) {
        sharedPreferencesRepository.saveString(SharedPreferencesRepository.JWT_TOKEN_KEY, token)
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return username.length in 1..60
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length in 6..100
    }
}