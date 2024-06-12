package com.example.autistalk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.autistalk.data.*
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

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchCards(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        fab.setOnClickListener {
            startActivity(Intent(this, CreateCardActivity::class.java))
        }

        viewModel.cards.observe(this, Observer { cards ->
            Log.d("MainActivity", "Received cards: $cards") // Check if cards is empty
            adapter.submitList(cards)
        })

        viewModel.getAllCards()
    }
}