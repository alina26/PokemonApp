package com.example.pokedex.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedex.R
import com.example.pokedex.presentation.details.PokemonDetailsFragment
import com.example.pokedex.presentation.list.PokemonListFragment

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

    }
}
