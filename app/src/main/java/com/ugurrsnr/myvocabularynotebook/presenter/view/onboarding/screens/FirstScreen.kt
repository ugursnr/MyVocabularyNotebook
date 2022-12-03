package com.ugurrsnr.myvocabularynotebook.presenter.view.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.ugurrsnr.myvocabularynotebook.R
import com.ugurrsnr.myvocabularynotebook.databinding.FragmentFirstScreenBinding

//OnBoard
class FirstScreen : Fragment() {
    private var _binding : FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstScreenBinding.inflate(layoutInflater,container,false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.next1.setOnClickListener {
            viewPager?.currentItem = 1 //first screen is 0 so when clicked next we increase the number

        }
        return binding.root
    }


}