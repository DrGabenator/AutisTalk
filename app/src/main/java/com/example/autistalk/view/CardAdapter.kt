package com.example.autistalk.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.autistalk.R
import com.example.autistalk.data.Card

class CardAdapter(
    private val cards: MutableList<Card>,
    private val onPlayClickListener: (String) -> Unit
) : RecyclerView.Adapter<CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        holder.text.text = card.getText()
        holder.emoji.text = card.getEmojiCharSequence()
        holder.itemView.setBackgroundColor(card.getBackground().toColorInt())

        holder.playButton.setOnClickListener {
            onPlayClickListener(card.getText())
        }
    }

    fun updateData(newCards: List<Card>) {
        cards.clear()
        cards.addAll(newCards)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = cards.size
}

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val text: TextView = itemView.findViewWithTag(R.id.card_text)
    val emoji: TextView = itemView.findViewById(R.id.card_emoji)
    val playButton: Button = itemView.findViewById(R.id.play_button)
}