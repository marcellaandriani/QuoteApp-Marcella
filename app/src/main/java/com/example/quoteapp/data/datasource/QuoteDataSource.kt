package com.example.quoteapp.data.datasource

import com.example.quoteapp.data.source.network.model.QuoteResponse
import com.example.quoteapp.data.source.network.services.QuoteApiServices

interface QuoteDataSource {
    suspend fun getRandomQuotes(): List<QuoteResponse>
}

class QuoteApiDataSource( private val service : QuoteApiServices ) : QuoteDataSource{
    override suspend fun getRandomQuotes(): List<QuoteResponse> {
        return service.getRandomQuotes()
    }

}