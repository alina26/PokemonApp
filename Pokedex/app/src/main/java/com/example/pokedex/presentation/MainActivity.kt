package com.example.pokedex.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedex.presentation.details.PokemonDetailsFragment
import com.example.pokedex.presentation.list.PokemonListFragment

class MainActivity : AppCompatActivity(), Navigation {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, PokemonListFragment())
            .commit()
    }
    override fun openPokemonDetails(id: String) {
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, PokemonDetailsFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }
}

interface Navigation {
    fun openPokemonDetails(id: String)
}