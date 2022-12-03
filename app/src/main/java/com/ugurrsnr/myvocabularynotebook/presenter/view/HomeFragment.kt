package com.ugurrsnr.myvocabularynotebook.presenter.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ugurrsnr.myvocabularynotebook.databinding.FragmentHomeBinding
import com.ugurrsnr.myvocabularynotebook.presenter.adapter.VocabulariesHomeAdapter
import com.ugurrsnr.myvocabularynotebook.presenter.viewmodel.AddVocabularySharedViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var vocabularyAdapter = VocabulariesHomeAdapter()
    private lateinit var sharedViewModel: AddVocabularySharedViewModel

    lateinit var mAdView : AdView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdView= binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        //sharedViewModel = ViewModelProvider(this)[AddVocabularySharedViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity()).get(AddVocabularySharedViewModel::class.java)
        sharedViewModel.getAllVocabulariesFromDB()
        prepareRecyclerView()
        observeAllVocabularies()

        addVocabularyOnClick()

        vocabularyAdapter.apply {
            onItemDeleteClicked = {
                sharedViewModel.deleteVocabulary(it)
                sharedViewModel.getAllVocabulariesFromDB()
                observeAllVocabularies()
            }
            onItemClicked = {
                val action = HomeFragmentDirections.actionHomeFragmentToAddVocabularyBottomSheetFragment()
                action.vocabularyID = it.vocabularyID
                Navigation.findNavController(requireView()).navigate(action)
            }
        }



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

    private fun observeAllVocabularies(){

        sharedViewModel.allVocabulariesLiveData.observe(viewLifecycleOwner, Observer {

            vocabularyAdapter.updateVocabularyList(it)

        })
    }


}
/*

 */