package com.ugurrsnr.myvocabularynotebook.presenter.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.ugurrsnr.myvocabularynotebook.R
import com.ugurrsnr.myvocabularynotebook.databinding.FragmentSplashScreenBinding
import com.ugurrsnr.myvocabularynotebook.presenter.MainActivity

class SplashScreenFragment : Fragment() {

    private var _binding : FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)

        val backgroundImg = binding.ivLogo
        val sideAnimation = AnimationUtils.loadAnimation(context, R.anim.slide)
        backgroundImg.startAnimation(sideAnimation)


        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler().postDelayed({

            if (onBoardingFinished()) {
                findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment())
            } else {
                findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToViewPagerFragment())

            }
        }, 2000)

        return binding.root
    }


    private fun onBoardingFinished() : Boolean{
        val sharedPref= requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}







