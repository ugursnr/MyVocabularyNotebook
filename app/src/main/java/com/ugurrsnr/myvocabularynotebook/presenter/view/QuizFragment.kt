package com.ugurrsnr.myvocabularynotebook.presenter.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ugurrsnr.myvocabularynotebook.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    private var _binding : FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(layoutInflater,container, false)
        return binding.root
    }


}