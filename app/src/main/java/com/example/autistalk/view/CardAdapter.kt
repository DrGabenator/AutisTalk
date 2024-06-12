package com.example.autistalk.view

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.autistalk.R
import com.example.autistalk.data.Card

class CardAdapter(private val viewModel: MainViewModel) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    private val cards = mutableListOf<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        holder.cardTextTextView.text = card.text
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CardDetailActivity::class.java)
            intent.putExtra("card_id", card.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun submitList(cards: List<Card>) {
        this.cards.clear()
        this.cards.addAll(cards)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardTextTextView: TextView = itemView.findViewById(R.id.cardTextTextView)
    }
}