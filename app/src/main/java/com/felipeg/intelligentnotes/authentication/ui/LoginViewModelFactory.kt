package com.felipeg.intelligentnotes.authentication.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.felipeg.intelligentnotes.authentication.data.LoginDataSource
import com.felipeg.intelligentnotes.authentication.data.LoginRepository
import com.felipeg.intelligentnotes.common.SharedPreferencesRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(private val sharedPreferences: SharedPreferences) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource()
                ),
                sharedPreferencesRepository = SharedPreferencesRepository(
                    sharedPreferences
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}