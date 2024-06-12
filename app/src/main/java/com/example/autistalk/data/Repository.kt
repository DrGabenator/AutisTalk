package com.example.autistalk.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CardRepository(private val cardDao: CardDao) {
    suspend fun getAllCards(): List<Card>? = withContext(Dispatchers.IO) { cardDao.getAllCards() }
    suspend fun insertCard(card: Card) = withContext(Dispatchers.IO) { cardDao.insertCard(card) }
    suspend fun getCardByCardId(cardId: Int) = withContext(Dispatchers.IO) { cardDao.getCardByCardId(cardId) }
    fun searchCards(query: String): List<Card>? {
        return cardDao.searchCards(query)
    }
}