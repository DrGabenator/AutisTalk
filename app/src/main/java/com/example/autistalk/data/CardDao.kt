package com.example.autistalk.data

import androidx.room.*

@Dao
interface CardDao {
    @Query("SELECT * FROM card")
    fun getAllCards(): List<Card>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createOrUpdateCard(card: Card)

    @Query("SELECT * FROM card WHERE text LIKE :query")
    fun searchCards(query: String): List<Card>?

    @Query("SELECT * FROM card WHERE id = :cardId")
    fun getCardByCardId(cardId: Int): Card
}