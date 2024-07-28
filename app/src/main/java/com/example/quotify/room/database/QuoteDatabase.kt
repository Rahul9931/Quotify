package com.example.quotify.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quotify.model.Result
import com.example.quotify.room.dao.QuoteDao

@Database(entities = [Result::class], version = 1)
abstract class QuoteDatabase:RoomDatabase() {

    abstract fun quoteDao():QuoteDao

    companion object{
        @Volatile
        private var INSTANCE:QuoteDatabase? = null

        fun getDatabase(context: Context):QuoteDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context,QuoteDatabase::class.java,"QuoteDB").build()
            }
            return INSTANCE!!
        }
    }

}