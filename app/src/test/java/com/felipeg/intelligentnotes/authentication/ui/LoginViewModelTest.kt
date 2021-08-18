package com.felipeg.intelligentnotes.authentication.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.felipeg.intelligentnotes.MainCoroutineRule
import com.felipeg.intelligentnotes.authentication.data.LoginDataSource
import com.felipeg.intelligentnotes.authentication.data.LoginRepository
import com.felipeg.intelligentnotes.authentication.data.model.LoggedInUser
import com.felipeg.intelligentnotes.common.Result
import com.felipeg.intelligentnotes.common.SharedPreferencesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

class LoginViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `login with valid credentials and save token on shared prefs`() {
        val token =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxLGZlbGlwZSIsImlzcyI6ImludGVsbGlnZW50X25vdGVzIiwiaWF0IjoxNjI5MDY4ODU3LCJleHAiOjE2MjkxNTUyNTd9.HKvMP2FK_NXGyjUoIgCjU760cTBaE52Miop8WwguI-S3WeTaw1Bum5ZSYqSAmD1FLIG2V0OUBRmpT58abpSJzA"
        val username = "validuser123"
        val password = "validpass321"


        val loginDataSource = mock<LoginDataSource>().stub {
            onBlocking {
                login(username, password)
            } doReturn Result.Success(LoggedInUser("1", username, token))
        }

        val loginRepository = LoginRepository(loginDataSource)
        val sharedPreferencesRepositoryMock: SharedPreferencesRepository = mock()
        val loginViewModel = LoginViewModel(loginRepository, sharedPreferencesRepositoryMock)
        loginViewModel.login(username, password)

        Thread.sleep(30)

        verify(
            sharedPreferencesRepositoryMock,
            times(1)
        ).saveString(SharedPreferencesRepository.JWT_TOKEN_KEY, token)
    }

    @Test
    fun `error on login`() {
        val token =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxLGZlbGlwZSIsImlzcyI6ImludGVsbGlnZW50X25vdGVzIiwiaWF0IjoxNjI5MDY4ODU3LCJleHAiOjE2MjkxNTUyNTd9.HKvMP2FK_NXGyjUoIgCjU760cTBaE52Miop8WwguI-S3WeTaw1Bum5ZSYqSAmD1FLIG2V0OUBRmpT58abpSJzA"
        val username = "invalid"
        val password = "123456"


        val loginDataSource = mock<LoginDataSource>().stub {
            onBlocking {
                login(username, password)
            } doReturn Result.Error(Exception("Failed to login"))
        }

        val loginRepository = LoginRepository(loginDataSource)
        val sharedPreferencesRepositoryMock: SharedPreferencesRepository = mock()
        val loginViewModel = LoginViewModel(loginRepository, sharedPreferencesRepositoryMock)
        loginViewModel.login(username, password)

        Thread.sleep(30)

        verify(
            sharedPreferencesRepositoryMock,
            times(0)
        ).saveString(SharedPreferencesRepository.JWT_TOKEN_KEY, token)
    }
}