package com.ugurrsnr.myvocabularynotebook.data.local

import androidx.room.*
import com.ugurrsnr.myvocabularynotebook.domain.model.Vocabulary

@Dao
interface VocabularyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertVocabulary(vocabulary: Vocabulary)

    @Update
    fun updateVocabulary(vocabulary : Vocabulary)

    @Delete
    fun deleteVocabulary(vocabulary: Vocabulary)

    @Query("SELECT * FROM vocabularyTable")
    fun getAllVocabulariesFromDB() : List<Vocabulary>

    @Query("SELECT * FROM vocabularyTable WHERE vocabularyID = :vocabularyID")
    fun getVocabularyDetailsByID(vocabularyID : Int) : List<Vocabulary>



}