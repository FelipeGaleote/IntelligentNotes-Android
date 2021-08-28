package com.felipeg.intelligentnotes.annotation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felipeg.intelligentnotes.annotation.data.AnnotationRepository
import com.felipeg.intelligentnotes.annotation.data.model.Annotation
import com.felipeg.intelligentnotes.common.SharedPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyAnnotationsViewModel(private val annotationRepository: AnnotationRepository,private val sharedPreferencesRepository: SharedPreferencesRepository) : ViewModel() {

    private val _myNotesResult = MutableLiveData<List<Annotation>>()
    val myNotesResult: LiveData<List<Annotation>> = _myNotesResult

    fun listMyNotes() {
        CoroutineScope(Dispatchers.IO).launch {
            val token = sharedPreferencesRepository.getString(SharedPreferencesRepository.JWT_TOKEN_KEY)
            val myNotes = annotationRepository.listMyNotes(token!!)
            withContext(Dispatchers.Main) {
                _myNotesResult.value = myNotes
            }
        }
    }
}