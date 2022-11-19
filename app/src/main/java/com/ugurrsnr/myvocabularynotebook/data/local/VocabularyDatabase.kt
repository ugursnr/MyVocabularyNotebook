package com.ugurrsnr.myvocabularynotebook.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ugurrsnr.myvocabularynotebook.domain.model.Vocabulary

@Database(entities = [Vocabulary::class], version = 1)
abstract class VocabularyDatabase : RoomDatabase() {

    abstract fun vocabularyDao() : VocabularyDao

    companion object {
        @Volatile
        private var instance : VocabularyDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?:synchronized(lock){
            instance ?: makeDatabase(context).also{
                instance = it
            }
        }


        fun makeDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,
            VocabularyDatabase::class.java, "routineTasksDatabase").build()
    }


}