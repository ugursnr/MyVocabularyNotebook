package com.ugurrsnr.myvocabularynotebook.presenter.view.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ugurrsnr.myvocabularynotebook.R
import com.ugurrsnr.myvocabularynotebook.databinding.FragmentViewPagerBinding
import com.ugurrsnr.myvocabularynotebook.presenter.view.onboarding.screens.FirstScreen
import com.ugurrsnr.myvocabularynotebook.presenter.view.onboarding.screens.SecondScreen


class ViewPagerFragment : Fragment() {
    private var _binding : FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(layoutInflater,container,false)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),

        )
        val adapterM= ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapterM
        return binding.root
    }


}