package com.felipeg.intelligentnotes.common

import android.content.SharedPreferences

class SharedPreferencesRepository(private val sharedPreferences: SharedPreferences) {

    companion object {
        val PREFERENCE_FILE_KEY = "com.felipeg.intelligentnotes.PREFERENCE_FILE_KEY"
        val JWT_TOKEN_KEY = "jwt_token_key"
    }

    fun saveString(key: String, value: String) {
        val sharedPrefEditor = this.sharedPreferences.edit()
        sharedPrefEditor.putString(key, value)
        sharedPrefEditor.apply()
    }

}