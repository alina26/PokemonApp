package com.example.pokedex.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.presentation.adapter.MainAdapter
import com.example.pokedex.R

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel = MainViewModel()
    private val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        viewModel.getPokemonList().observe(this){ pokemonList ->
            adapter.setPokemonList(pokemonList)
        }

        viewModel.loadData()

        val manager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) =  when (position) {
                0 -> 2
                else -> 1
            }
        }
        //recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
    }
}