package com.example.quoteapp.presentation.main.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.quoteapp.R
import com.example.quoteapp.base.ViewHolderBinder
import com.example.quoteapp.data.model.Quote
import com.example.quoteapp.databinding.ItemQuoteBinding

class QuoteViewHolder(private val binding: ItemQuoteBinding) : RecyclerView.ViewHolder(binding.root),
    ViewHolderBinder<Quote> {
    override fun bind(item: Quote) {
        binding.tvQuote.text = item.quote
        binding.tvCharacter.text = item.character
        binding.tvAnime.text = item.anime
    }
}
