package com.example.quotify.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.quotify.R
import com.example.quotify.api.QuoteService
import com.example.quotify.api.RetrofitHelper
import com.example.quotify.databinding.ActivityMainBinding
import com.example.quotify.model.Quotes
import com.example.quotify.model.Result
import com.example.quotify.repository.QuoteRepository
import com.example.quotify.utils.QuoteApplicatoin
import com.example.quotify.viewmodel.QuoteViewModel
import com.example.quotify.viewmodel.QuoteViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var index = 0
    private var resultList:MutableList<Result> = mutableListOf()
    private lateinit var quoteViewModel:QuoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.shimmer.startShimmer()
        binding.shimmer.visibility = View.VISIBLE
        binding.container.visibility = View.GONE
        val repository = (application as QuoteApplicatoin).quoteRepository
        quoteViewModel = ViewModelProvider(this,QuoteViewModelFactory(repository)).get(QuoteViewModel::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            setQuote(quoteViewModel.getQuote())

        }


        //Log.d("check_2","${resultList}")
        binding.btnNext.setOnClickListener {
            setQuote(quoteViewModel.nextQuote())
        }
        binding.btnPrevious.setOnClickListener {
            setQuote(quoteViewModel.previousQuote())
        }
        binding.btnShare.setOnClickListener {
            lifecycleScope.launch {
                val sendIntent = Intent(Intent.ACTION_SEND)
                sendIntent.setType("text/plain")
                sendIntent.putExtra(Intent.EXTRA_TEXT,quoteViewModel.getQuote().content)
                startActivity(sendIntent)
            }

        }
    }

    private fun setQuote(quote: Result) {
        Log.d("check_res1","${quote}")
        Log.d("check_res2","${quote.length}")
        if (quote.length == 0){
            Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            },2000)
        }
        binding.shimmer.stopShimmer()
        binding.shimmer.visibility = View.GONE
        binding.container.visibility = View.VISIBLE
        binding.txtQuote.text = quote.content
        binding.txtQuoteAuthor.text = quote.author
    }

}