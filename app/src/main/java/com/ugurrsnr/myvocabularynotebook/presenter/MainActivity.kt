package com.ugurrsnr.myvocabularynotebook.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ugurrsnr.myvocabularynotebook.R
import com.ugurrsnr.myvocabularynotebook.presenter.viewmodel.AddVocabularySharedViewModel

/**
 * Onboard Screens
 * Splash Screen
 */

class MainActivity : AppCompatActivity() {
    lateinit var sharedViewModel: AddVocabularySharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedViewModel = ViewModelProvider(this)[AddVocabularySharedViewModel::class.java]

    }
}