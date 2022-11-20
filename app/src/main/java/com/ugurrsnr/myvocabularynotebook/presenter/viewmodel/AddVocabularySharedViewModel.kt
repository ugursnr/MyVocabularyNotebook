package com.ugurrsnr.myvocabularynotebook.presenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ugurrsnr.myvocabularynotebook.data.local.VocabularyDatabase
import com.ugurrsnr.myvocabularynotebook.domain.model.Vocabulary
import com.ugurrsnr.myvocabularynotebook.domain.repo.VocabularyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddVocabularySharedViewModel(application: Application) : AndroidViewModel(application) {

    private var _allVocabulariesLiveData = MutableLiveData<List<Vocabulary>>()
    private var _vocabularyLiveData = MutableLiveData<Vocabulary>()

    val allVocabulariesLiveData get() = _allVocabulariesLiveData
    val vocabularyLiveData get() = _vocabularyLiveData

    val dao = VocabularyDatabase.makeDatabase(application).vocabularyDao()
    val repository = VocabularyRepository(dao)

    fun insertVocabulary(vocabulary: Vocabulary) = CoroutineScope(Dispatchers.IO).launch {
        repository.insertVocabulary(vocabulary)
    }
    fun updateVocabulary(vocabulary: Vocabulary) = CoroutineScope(Dispatchers.IO).launch {
        repository.updateVocabulary(vocabulary)
    }
    fun deleteVocabulary(vocabulary: Vocabulary) = CoroutineScope(Dispatchers.IO).launch {
        repository.deleteVocabulary(vocabulary)
    }

    fun getAllVocabulariesFromDB() = CoroutineScope(Dispatchers.IO).launch {
        val temp = repository.getAllVocabulariesFromDB()

        withContext(Dispatchers.Main){
            _allVocabulariesLiveData.value = temp
        }

    }

    fun getVocabularyDetailsByID(vocabularyID : Int) = CoroutineScope(Dispatchers.IO).launch {

        val temp = repository.getVocabularyDetailsByID(vocabularyID).first()

        withContext(Dispatchers.Main){
            _vocabularyLiveData.value = temp
        }

    }

    /*



3

    fun getVocabularyDetailsByID(vocabularyID : Int) : List<Vocabulary> {
        return dao.getVocabularyDetailsByID(vocabularyID)
    }
     */


}