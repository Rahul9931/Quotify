package com.example.quotify.utils

import android.app.Application
import com.example.quotify.api.QuoteService
import com.example.quotify.api.RetrofitHelper
import com.example.quotify.repository.QuoteRepository
import com.example.quotify.room.database.QuoteDatabase

class QuoteApplicatoin : Application() {

    lateinit var quoteRepository: QuoteRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
    }
    private fun initialize() {
        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val database = QuoteDatabase.getDatabase(applicationContext)
        quoteRepository = QuoteRepository(quoteService,database,applicationContext)
    }
}