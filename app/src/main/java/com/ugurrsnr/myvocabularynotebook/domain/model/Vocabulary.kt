package com.ugurrsnr.myvocabularynotebook.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vocabularyTable")
data class Vocabulary(
    @ColumnInfo(name = "vocabulary")
    val vocabulary : String? = null,
    @ColumnInfo(name = "vocabularyTranslation")
    val vocabularyTranslation : String? = null,
    @ColumnInfo(name = "sampleSentence")
    val sampleSentence : String? = null
){
    @PrimaryKey(autoGenerate = true)
    var vocabularyID : Int = 0
}
