package com.example.autistalk.view

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.autistalk.R
import com.example.autistalk.data.Card
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class CardDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var card: Card
    private lateinit var tts: TextToSpeech
    private lateinit var cardTextTextView: TextView
    private lateinit var playButton: FloatingActionButton
    private lateinit var editButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        supportActionBar?.hide()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        viewModel = ViewModelProvider(this, MainViewModelFactory(application))[MainViewModel::class.java]

        val cardId = intent.getIntExtra("card_id", 0)

        intent.putExtra("card_id", cardId)

        viewModel.getAllCards()

        viewModel.getCardByCardId(cardId = cardId)

        viewModel.cards.observe(this) { cards ->
            cards.forEach { card: Card ->
                if (cardId == card.id) {
                    this.card = card
                    cardTextTextView.text = card.text
                }
            }
        }

        cardTextTextView = findViewById(R.id.cardTextTextView)
        playButton = findViewById(R.id.playButton)
        editButton = findViewById(R.id.editButton)

        tts = TextToSpeech(this, null)

        playButton.setOnClickListener {
            val text = cardTextTextView.text.toString()
            tts.language = Locale("pt", "BR")
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }

        editButton.setOnClickListener {
            startActivity(Intent(this, CreateCardActivity::class.java).putExtra("card_id", cardId))
        }
    }

    override fun onResume() {
        super.onResume()

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        viewModel = ViewModelProvider(this, MainViewModelFactory(application))[MainViewModel::class.java]

        val cardId = intent.getIntExtra("card_id", 0)

        intent.putExtra("card_id", cardId)

        viewModel.getAllCards()

        viewModel.getCardByCardId(cardId = cardId)

        viewModel.cards.observe(this) { cards ->
            cards.forEach { card: Card ->
                if (cardId == card.id) {
                    this.card = card
                    cardTextTextView.text = card.text
                }
            }
        }
    }
}