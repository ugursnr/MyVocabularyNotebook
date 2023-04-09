package com.ugurrsnr.myvocabularynotebook.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.lifecycle.ViewModelProvider
import com.ugurrsnr.myvocabularynotebook.R
import com.ugurrsnr.myvocabularynotebook.core.util.MyPreference
import com.ugurrsnr.myvocabularynotebook.presenter.viewmodel.PremiumViewModel

/**
 * Onboard Screens
 * Splash Screen
 */

class MainActivity : AppCompatActivity() {
    lateinit var myPreference : MyPreference
    lateinit var premiumViewModel: PremiumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        setContentView(R.layout.activity_main)
        actionBar?.isHideOnContentScrollEnabled = true

        premiumViewModel = ViewModelProvider(this)[PremiumViewModel::class.java]

    }


}