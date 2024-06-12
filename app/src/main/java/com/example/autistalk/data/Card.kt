package com.example.autistalk.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Card(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var text: String)