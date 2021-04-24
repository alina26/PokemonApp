package com.example.pokedex.presentation.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.presentation.list.adapter.MainAdapter
import com.example.pokedex.R
import com.example.pokedex.presentation.MainViwModel
import com.example.pokedex.presentation.details.PokemonDetailsActivity
import com.example.pokedex.presentation.list.adapter.DisplayableItem

class MainActivity : AppCompatActivity() {

    private val viewModel = MainViwModel()
    private var adapter:MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        viewModel.getPokemonList().observe(this){
            showData(it)
        }

        viewModel.error().observe(this) {
            showError(it)
        }

        viewModel.loading().observe(this) {
            showProgress()
        }

        viewModel.loadData()
    }

    private fun initRecyclerView() {

        adapter = MainAdapter(
            onItemClicked = { id ->

                startActivity(PokemonDetailsActivity.intent(this, id))
            }
        )
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val manager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
    }

   private fun showProgress() {
        Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()
    }

   private fun showData(items: List<DisplayableItem>) {
        adapter?.setPokemonList(items)
    }

    private fun showError(errorMessage: String) {

    }
}