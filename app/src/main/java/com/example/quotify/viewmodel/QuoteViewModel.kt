package com.example.quotify.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotify.model.Quotes
import com.example.quotify.model.Result
import com.example.quotify.repository.QuoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class QuoteViewModel(private val repository: QuoteRepository):ViewModel() {

    var quotesList:List<Result> = emptyList()
    private var index = 0
    init {

    }

    suspend fun getQuote(): Result {
        val job = viewModelScope.launch {
            quotesList = repository.getQuote(1)
            Log.d("check_vm1","$quotesList")
            Log.d("check_vm1","${quotesList.size}")
        }
        job.join()
        if (quotesList.isEmpty()){
            Log.d("check_inner","${quotesList.size}")
            val result:Result = Result(0,"","","","","","",0)
            return result
        }
        Log.d("check_ind1","${index}")
        return quotesList[index]
    }

    fun nextQuote():Result{
        if (index==quotesList.size-1){
            index = -1
        }
        ++index
        Log.d("check_ind2","${index}")
        return quotesList[index]
    }

    fun previousQuote():Result{
        if (index==0){
            index = quotesList.size
        }
        --index
        Log.d("check_ind3","${index}")
        return quotesList[index]
    }


}