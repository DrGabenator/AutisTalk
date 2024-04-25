package com.example.autistalk.view

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.autistalk.data.Card
import com.example.autistalk.data.CardRepository

class CardListViewModel(application: Application) : ViewModel() {

    private val repository: CardRepository = CardRepository(application)

    fun getCards(categoryId: Int): LiveData<List<Card>> {
        return repository.getCardsByCategory(categoryId)
    }
}