package com.example.autistalk.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CardDao {
    @Query("SELECT * FROM card")
    fun getAllCards(): List<Card>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(card: Card)

    @Query("SELECT * FROM card WHERE text LIKE :query")
    fun searchCards(query: String): List<Card>?

    @Query("SELECT * FROM card WHERE id = :cardId")
    fun getCardByCardId(cardId: Int): Card
}