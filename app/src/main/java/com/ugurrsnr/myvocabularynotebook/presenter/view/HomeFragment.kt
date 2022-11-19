package com.ugurrsnr.myvocabularynotebook.presenter.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ugurrsnr.myvocabularynotebook.R
import com.ugurrsnr.myvocabularynotebook.databinding.FragmentHomeBinding
import com.ugurrsnr.myvocabularynotebook.presenter.adapter.VocabulariesHomeAdapter


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var vocabularyAdapter = VocabulariesHomeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        addVocabularyOnClick()
    }


    private fun prepareRecyclerView(){

        binding.recyclerViewHome.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = vocabularyAdapter
        }
    }

    private fun addVocabularyOnClick(){
        binding.addVocabularyButton.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToAddVocabularyBottomSheetFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}