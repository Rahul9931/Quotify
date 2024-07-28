package com.example.quotify.api

import com.example.quotify.model.Quotes
import com.example.quotify.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteService {

    @GET("quotes")
    suspend fun getQuote(@Query("page")page:Int):Response<Quotes>
}