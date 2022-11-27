package com.ugurrsnr.myvocabularynotebook.data.local

import androidx.room.*
import com.ugurrsnr.myvocabularynotebook.domain.model.Vocabulary

@Dao
interface VocabularyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertVocabulary(vocabulary: Vocabulary)

    @Query("UPDATE vocabularyTable SET " +
            "vocabulary = :vocabularyName, " +
            "vocabularyTranslation = :vocabularyTranslation, " +
            "sampleSentence = :sampleSentence " +
            "WHERE vocabularyID = :vocabularyID")
    fun updateVocabulary(vocabularyID : Int, vocabularyName : String?, vocabularyTranslation : String?, sampleSentence : String?)

    @Delete
    fun deleteVocabulary(vocabulary: Vocabulary)

    @Query("SELECT * FROM vocabularyTable")
    fun getAllVocabulariesFromDB() : List<Vocabulary>

    @Query("SELECT * FROM vocabularyTable WHERE vocabularyID = :vocabularyID")
    fun getVocabularyDetailsByID(vocabularyID : Int) : List<Vocabulary>



}