package com.example.quotify.repository

import android.content.Context
import android.util.Log
import com.example.quotify.api.QuoteService
import com.example.quotify.model.Result
import com.example.quotify.room.database.QuoteDatabase
import com.example.quotify.utils.NetworkUtils

class QuoteRepository(
    private val quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context
) {

    /*private val quotesLiveData = MutableLiveData<List<Result>>()

    val quotes:LiveData<List<Result>>
        get() = quotesLiveData*/

    suspend fun getQuote(page:Int): List<Result> {
        if (NetworkUtils.isInternetAvailable(applicationContext)){
            val result = quoteService.getQuote(page)
            if (result?.body()!=null){
                Log.d("check_api","${result.body()!!.results.size}")
                if (quoteDatabase.quoteDao().getQuote().isEmpty()){
                    quoteDatabase.quoteDao().addQuote(result.body()!!.results)
                    Log.d("check_db1","${quoteDatabase.quoteDao().getQuote().size}")
                }
                val resultbody = result.body()
                return resultbody?.results!!
            }
        }
        else{
            Log.d("check_db1","${quoteDatabase.quoteDao().getQuote().size}")
            return quoteDatabase.quoteDao().getQuote()
        }
        val list:List<Result> = listOf()
        return list
    }
}