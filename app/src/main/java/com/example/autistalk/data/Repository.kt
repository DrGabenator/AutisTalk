package com.example.autistalk.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CardRepository(private val cardDao: CardDao) {
    suspend fun getAllCards(): List<Card>? = withContext(Dispatchers.IO) { cardDao.getAllCards() }
    suspend fun createOrUpdateCard(card: Card) = withContext(Dispatchers.IO) { cardDao.createOrUpdateCard(card) }
    suspend fun getCardByCardId(cardId: Int) = withContext(Dispatchers.IO) { cardDao.getCardByCardId(cardId) }
    fun searchCards(query: String): List<Card>? {
        return cardDao.searchCards(query)
    }
}