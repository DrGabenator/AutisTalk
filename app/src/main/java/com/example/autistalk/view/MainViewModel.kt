package com.example.autistalk.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.autistalk.data.AppDatabase
import com.example.autistalk.data.Card
import com.example.autistalk.data.CardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> get() = _cards

    private var cardRepository: CardRepository

    init {
        val database = AppDatabase.getInstance(application)
        val cardDao = database.cardDao()
        cardRepository = CardRepository(cardDao)
    }

    fun searchCards(query: String) {
        viewModelScope.launch {
            try {
                val cards = cardRepository.searchCards(query)
                _cards.postValue(cards ?: listOf())
            } catch (_: Exception) {

            }
        }
    }

    fun createCard(text: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val card = Card(text = text)
                cardRepository.insertCard(card)
            } catch (_: Exception) {
            }
        }
    }

    fun getCardByCardId(cardId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val card = cardRepository.getCardByCardId(cardId)
                _cards.postValue(listOf(card))
            } catch (_: Exception) {
            }
        }
    }

    fun getAllCards() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cards = cardRepository.getAllCards()
                _cards.postValue(cards ?: listOf())

            } catch (_: Exception) {
            }
        }
    }

}