package com.felipeg.intelligentnotes.annotation.ui

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.felipeg.intelligentnotes.annotation.data.AnnotationDataSource
import com.felipeg.intelligentnotes.annotation.data.AnnotationRepository
import com.felipeg.intelligentnotes.common.SharedPreferencesRepository

class MyAnnotationsViewModelFactory(private val sharedPreferences: SharedPreferences) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyAnnotationsViewModel::class.java)) {
            return MyAnnotationsViewModel(
                annotationRepository = AnnotationRepository(
                    AnnotationDataSource()
                ),
                sharedPreferencesRepository = SharedPreferencesRepository(
                    sharedPreferences
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}