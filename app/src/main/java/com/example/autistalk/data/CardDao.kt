package com.example.autistalk.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CardDao {

    @Query("SELECT * FROM card")
    fun getAllCards(): LiveData<List<Card>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCard(card: Card)

    @Update
    suspend fun updateCard(card: Card)

    @Query("DELETE FROM card WHERE id = :cardId")
    suspend fun deleteCard(cardId: Int)
}