package com.ugurrsnr.myvocabularynotebook.presenter.view.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.ugurrsnr.myvocabularynotebook.R
import com.ugurrsnr.myvocabularynotebook.databinding.FragmentSecondScreenBinding
import com.ugurrsnr.myvocabularynotebook.presenter.view.onboarding.ViewPagerFragmentDirections

class SecondScreen : Fragment() {

    private var _binding : FragmentSecondScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondScreenBinding.inflate(layoutInflater,container,false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.finish.setOnClickListener {
            findNavController().navigate(ViewPagerFragmentDirections.actionViewPagerFragmentToHomeFragment())
            onBoardingFinished()
        }

        return binding.root
    }

    fun onBoardingFinished(){
        val sharedPref= requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

}