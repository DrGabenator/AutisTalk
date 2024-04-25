package com.example.autistalk.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class Card(
    @PrimaryKey(autoGenerate = true) val id: Int,
    private var text: String,
    private var textColor: Int,
    private var emoji: String,
    private var background: String // Either image URL or solid color in hex format
) {
    fun getText(): String {
        return text
    }

    fun setText(text: String) {
        this.text = text
    }

    fun getTextColor(): Int {
        return textColor
    }

    fun setTextColor(textColor: Int) {
        this.textColor = textColor
    }

    fun getEmoji(): String {
        return emoji
    }

    fun getEmojiCharSequence(): CharSequence {
        return emoji
    }

    fun setEmoji(emoji: Int) {
        this.emoji = String()
    }

    fun getBackground(): String {
        return background
    }

    fun setBackground(background: String) {
        this.background = background
    }
}