package com.ugurrsnr.myvocabularynotebook.domain.repo

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.ugurrsnr.myvocabularynotebook.data.local.VocabularyDao
import com.ugurrsnr.myvocabularynotebook.domain.model.Vocabulary

class VocabularyRepository(val dao : VocabularyDao) {

    fun insertVocabulary(vocabulary: Vocabulary){
        dao.insertVocabulary(vocabulary)
    }

    fun updateVocabulary(vocabulary : Vocabulary){
        dao.updateVocabulary(vocabulary)
    }

    fun deleteVocabulary(vocabulary: Vocabulary){
        dao.deleteVocabulary(vocabulary)
    }

    fun getAllVocabulariesFromDB() : List<Vocabulary>{
        return dao.getAllVocabulariesFromDB()
    }

    fun getVocabularyDetailsByID(vocabularyID : Int) : List<Vocabulary> {
        return dao.getVocabularyDetailsByID(vocabularyID)
    }





}