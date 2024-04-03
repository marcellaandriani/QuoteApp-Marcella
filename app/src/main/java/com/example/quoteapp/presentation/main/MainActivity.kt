package com.example.quoteapp.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import com.example.quoteapp.R
import com.example.quoteapp.data.datasource.QuoteApiDataSource
import com.example.quoteapp.data.datasource.QuoteDataSource
import com.example.quoteapp.data.repository.QuoteRepository
import com.example.quoteapp.data.repository.QuoteRepositoryImpl
import com.example.quoteapp.data.source.network.services.QuoteApiServices
import com.example.quoteapp.databinding.ActivityMainBinding
import com.example.quoteapp.presentation.main.adapter.QuoteAdapter
import com.example.quoteapp.utils.GenericViewModelFactory
import com.example.quoteapp.utils.proceedWhen

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter: QuoteAdapter by lazy {
        QuoteAdapter()
    }

    private val viewModel: MainViewModel by viewModels {
        val s = QuoteApiServices.invoke()
        val ds: QuoteDataSource = QuoteApiDataSource(s)
        val rp: QuoteRepository = QuoteRepositoryImpl(ds)
        GenericViewModelFactory.create(MainViewModel(rp))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()
        getQuotes()
        observeData()
    }

    private fun setupRecyclerView() {
        binding.rvAnime.adapter = adapter
    }

    private fun getQuotes() {
        viewModel.getRandomQuotes()
    }

    private fun observeData() {
        viewModel.responseLiveData.observe(this) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvAnime.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvAnime.isVisible = true
                    result.payload?.let { result ->
                        adapter.submitData(result)
                    }
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                    binding.rvAnime.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                    binding.rvAnime.isVisible = false
                }
            )
        }
    }
}