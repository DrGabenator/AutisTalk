package com.example.autistalk.data

import android.app.Application
import androidx.lifecycle.LiveData

class CardRepository(private val application: Application) {

    private val cardDao: CardDao by lazy {
        val database = AppDatabase.getInstance(application.applicationContext)
        database.cardDao
    }

    suspend fun insertCard(card: Card) {
        return cardDao.insertCard(card)
    }

    suspend fun updateCard(card: Card) {
        cardDao.updateCard(card)
    }

    suspend fun deleteCard(cardId: Int) {
        cardDao.deleteCard(cardId)
    }
}