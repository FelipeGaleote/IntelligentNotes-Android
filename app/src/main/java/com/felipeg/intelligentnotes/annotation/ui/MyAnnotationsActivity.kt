package com.felipeg.intelligentnotes.annotation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.felipeg.intelligentnotes.common.SharedPreferencesRepository
import com.felipeg.intelligentnotes.databinding.ActivityMyAnnotationsBinding

class MyAnnotationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyAnnotationsBinding
    private lateinit var annotationsViewModel: MyAnnotationsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        annotationsViewModel = ViewModelProvider(
            this, MyAnnotationsViewModelFactory(
                applicationContext.getSharedPreferences(
                    SharedPreferencesRepository.PREFERENCE_FILE_KEY, MODE_PRIVATE
                )
            )
        ).get(MyAnnotationsViewModel::class.java)

        binding = ActivityMyAnnotationsBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}