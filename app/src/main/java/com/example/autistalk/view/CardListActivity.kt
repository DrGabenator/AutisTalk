package com.example.autistalk.view

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.autistalk.R
import com.example.autistalk.data.CardRepository
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var viewModel: CardListViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var searchBar: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_list_activity)

        viewModel = ViewModelProvider(this)[CardListViewModel::class.java]

        val cardRepository = CardRepository(application)

        val recyclerView = findViewById<RecyclerView>(R.id.card_list)
        recyclerView.adapter = CardAdapter(mutableListOf(), ::playText)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.getCards(DEFAULT_CATEGORY_ID).observe(this, Observer { cards ->
            (recyclerView.adapter as CardAdapter).updateData(cards)  // Cast to CardAdapter
        })

        toolbar = findViewById(R.id.toolbar)
        searchBar = findViewById(R.id.search_bar)
        toolbar.title = getString(R.string.app_name)  // Set app name from strings.xml

        // Handle menu button click (implementation depends on your menu structure)
        val menuButton = findViewById<ImageButton>(R.id.menu_button)
        menuButton.setOnClickListener {
            // Open your menu drawer or navigation fragment here
        }

        // Handle search query submission (use TextWatcher or OnQueryTextListener)
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Perform search based on the query text (call ViewModel or adapter)
                return false  // Handle search submission here (or return true to hide keyboard)
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search text change if needed (optional)
                return false
            }
        })

        // Initialize Text-to-Speech
        val textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val locale = Locale("pt", "BR")
                textToSpeech.language = locale
            }
        }
    }

    private fun playText(text: String) {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
    }

    companion object {
        const val DEFAULT_CATEGORY_ID = 1  // Define a default category ID (if applicable)
    }
}