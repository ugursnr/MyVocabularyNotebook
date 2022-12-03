package com.ugurrsnr.myvocabularynotebook.presenter.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ugurrsnr.myvocabularynotebook.R
import com.ugurrsnr.myvocabularynotebook.databinding.FragmentAddVocabularyBottomSheetBinding
import com.ugurrsnr.myvocabularynotebook.domain.model.Vocabulary
import com.ugurrsnr.myvocabularynotebook.presenter.viewmodel.AddVocabularySharedViewModel


class AddVocabularyBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding : FragmentAddVocabularyBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: AddVocabularySharedViewModel

    private var vocabularyInput : String? = null
    private var translationInput : String? = null
    private var sampleSentenceInput : String? = null

    private var vocabularyIDArgs : Int = -1
    private lateinit var vocabulary: Vocabulary

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddVocabularyBottomSheetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //sharedViewModel = ViewModelProvider(this)[AddVocabularySharedViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity()).get(AddVocabularySharedViewModel::class.java)

        arguments?.let {
            vocabularyIDArgs = AddVocabularyBottomSheetFragmentArgs.fromBundle(it).vocabularyID
        }

        if ( vocabularyIDArgs == -1){ //Checking the word is new or not
            binding.addOrUpdateVocabularyButton.setOnClickListener {

                vocabularyInput = binding.vocabularyActualET.text.toString()
                translationInput = binding.vocabularyTranslationET.text.toString()
                sampleSentenceInput = binding.vocabularySampleSentenceET.text.toString()
                println("dadasdasd")
                println(vocabularyInput)

                if(vocabularyInput == null || vocabularyInput == ""){
                    Toast.makeText(context, R.string.please_enter_a_vocabulary,Toast.LENGTH_LONG).show()
                }else{
                    val inputVocabulary = Vocabulary(vocabularyInput,translationInput,sampleSentenceInput)
                    insertVocabularyToDB(inputVocabulary)
                    Toast.makeText(context,R.string.succesfull_add,Toast.LENGTH_SHORT).show()
                    dismiss()
                    sharedViewModel.getAllVocabulariesFromDB()
                }

            }
        }else{
            //This block update an existing vocabulary

            sharedViewModel.getVocabularyDetailsByID(vocabularyIDArgs)
            sharedViewModel.vocabularyLiveData.observe(viewLifecycleOwner, Observer {

                val vocabularyName = it.vocabulary
                val vocabularyTranslation = it.vocabularyTranslation
                val vocabularySample = it.sampleSentence

                vocabulary = Vocabulary(vocabularyName, vocabularyTranslation, vocabularySample)
                vocabulary.vocabularyID = it.vocabularyID

                setUIWithVocabularyInfo(vocabularyName, vocabularyTranslation, vocabularySample)

            })

            binding.addOrUpdateVocabularyButton.apply {
                text = getText(R.string.update)
                setOnClickListener {

                    vocabularyInput = binding.vocabularyActualET.text.toString()
                    translationInput = binding.vocabularyTranslationET.text.toString()
                    sampleSentenceInput = binding.vocabularySampleSentenceET.text.toString()

                    val updatedVocabulary = Vocabulary(vocabularyInput, translationInput, sampleSentenceInput )
                    updatedVocabulary.vocabularyID = vocabularyIDArgs

                    updateVocabularyOnDB(vocabularyIDArgs,vocabularyInput ,translationInput,sampleSentenceInput)
                    Toast.makeText(context,R.string.successfull_update,Toast.LENGTH_SHORT).show()

                    dismiss()
                    sharedViewModel.getAllVocabulariesFromDB()
                }
            }

        }


    }

    private fun insertVocabularyToDB(vocabulary: Vocabulary){
        sharedViewModel.insertVocabulary(vocabulary)
    }

    private fun updateVocabularyOnDB(vocabularyID : Int, vocabularyName : String?, vocabularyTranslation : String?, sampleSentence : String?){
        sharedViewModel.updateVocabulary(vocabularyID, vocabularyName , vocabularyTranslation, sampleSentence)
    }

    private fun setUIWithVocabularyInfo(vocabularyName : String?, translation : String?, sample : String?){
        binding.apply {
            vocabularyActualET.setText(vocabularyName)
            vocabularyTranslationET.setText(translation)
            vocabularySampleSentenceET.setText(sample)
        }
    }

}