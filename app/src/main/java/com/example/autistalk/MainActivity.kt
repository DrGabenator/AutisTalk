package com.example.autistalk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.autistalk.data.Card
import com.example.autistalk.view.CardAdapter
import com.example.autistalk.view.CreateCardActivity
import com.example.autistalk.view.MainViewModel
import com.example.autistalk.view.MainViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CardAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var fab: FloatingActionButton

    private var originalCardList: List<Card> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        viewModel = ViewModelProvider(this, MainViewModelFactory(application))[MainViewModel::class.java]

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        fab = findViewById(R.id.fab)

        adapter = CardAdapter(viewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            startActivity(Intent(this, CreateCardActivity::class.java))
        }

        viewModel.getAllCards()

        viewModel.cards.observe(this, Observer { cards ->
            originalCardList = cards
            adapter.submitList(cards)
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.getAllCards()

        viewModel.cards.observe(this, Observer { cards ->
            adapter.submitList(cards)
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val filteredList = if (newText.isBlank()) {
                    originalCardList
                } else {
                    originalCardList.filter { card ->
                        card.text.contains(newText, ignoreCase = true)
                    }
                }
                adapter.submitList(filteredList)
                return false
            }
        })
    }
}