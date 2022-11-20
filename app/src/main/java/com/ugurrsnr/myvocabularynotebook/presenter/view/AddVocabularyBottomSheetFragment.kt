package com.ugurrsnr.myvocabularynotebook.presenter.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ugurrsnr.myvocabularynotebook.R
import com.ugurrsnr.myvocabularynotebook.databinding.FragmentAddVocabularyBottomSheetBinding
import com.ugurrsnr.myvocabularynotebook.domain.model.Vocabulary
import com.ugurrsnr.myvocabularynotebook.presenter.MainActivity
import com.ugurrsnr.myvocabularynotebook.presenter.viewmodel.AddVocabularySharedViewModel


class AddVocabularyBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding : FragmentAddVocabularyBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: AddVocabularySharedViewModel

    private var vocabularyInput : String? = null
    private var translationInput : String? = null
    private var sampleSentenceInput : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddVocabularyBottomSheetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(this)[AddVocabularySharedViewModel::class.java]

        binding.addOrUpdateVocabularyButton.setOnClickListener {
            vocabularyInput = binding.vocabularyActualET.text.toString()
            translationInput = binding.vocabularyTranslationET.text.toString()
            sampleSentenceInput = binding.vocabularySampleSentenceET.text.toString()

            val inputVocabulary = Vocabulary(vocabularyInput,translationInput,sampleSentenceInput)
            insertVocabularyToDB(inputVocabulary)

            dismiss()

        }

    }

    private fun insertVocabularyToDB(vocabulary: Vocabulary){
        sharedViewModel.insertVocabulary(vocabulary)
    }




    companion object {
        @JvmStatic
        fun newInstance() = AddVocabularyBottomSheetFragment()
    }
}