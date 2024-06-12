package com.example.autistalk.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.autistalk.R

class CreateCardActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var createCardButton: Button
    private lateinit var cardTextEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

        supportActionBar?.hide()

        viewModel = ViewModelProvider(this, MainViewModelFactory(application))[MainViewModel::class.java]

        createCardButton = findViewById(R.id.createCardButton)
        cardTextEditText = findViewById(R.id.cardTextEditText)

        createCardButton.setOnClickListener {
            val text = cardTextEditText.text.toString()
            viewModel.createCard(text)
            finish()
        }
    }
}