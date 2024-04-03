package com.example.quoteapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteapp.data.model.Quote
import com.example.quoteapp.data.repository.QuoteRepository
import com.example.quoteapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    private val _responseLiveData = MutableLiveData<ResultWrapper<List<Quote>>>()
    val responseLiveData: LiveData<ResultWrapper<List<Quote>>>
        get() = _responseLiveData

    fun getRandomQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            quoteRepository.getRandomQuotes().collect {
                _responseLiveData.postValue(it)
            }
        }
    }
}