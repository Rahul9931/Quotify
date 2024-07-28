package com.example.quotify.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quotify.model.Result

@Dao
interface QuoteDao {
    @Insert
    suspend fun addQuote(Quote:List<Result>)

    @Query("SELECT * FROM quote_table")
    suspend fun getQuote():List<Result>
}