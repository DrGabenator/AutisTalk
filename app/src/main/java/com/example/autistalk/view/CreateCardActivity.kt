package com.example.autistalk.view

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.autistalk.MainActivity
import com.example.autistalk.R
import com.example.autistalk.data.Card
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class CreateCardActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var createCardButton: MaterialButton
    private lateinit var cardTextEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

        supportActionBar?.hide()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        viewModel = ViewModelProvider(this, MainViewModelFactory(application))[MainViewModel::class.java]

        val cardId = intent.getIntExtra("card_id", 0)

        createCardButton = findViewById(R.id.createCardButton)
        cardTextEditText = findViewById(R.id.cardTextEditText)

        if (cardId != 0) {
            createCardButton.text = "Atualizar cartão"
        }

        createCardButton.setOnClickListener {

            if (cardId != 0) {
                val text = cardTextEditText.text.toString()
                val card = Card(text = text, id = cardId)
                viewModel.createOrUpdateCard(card)
                startActivity(Intent(this, MainActivity::class.java).putExtra("card_id", cardId))

                finish()
            } else {
                val text = cardTextEditText.text.toString()
                val card = Card(text = text)
                viewModel.createOrUpdateCard(card)
                finish()
            }
        }
    }
}