package com.example.pokedex.presentation.list

import com.example.pokedex.presentation.list.adapter.PokemonItem

sealed class PokemonListViewState {

    object Loading: PokemonListViewState()
    data class Error(val message: String): PokemonListViewState()
    data class Data(val items: List<PokemonItem>): PokemonListViewState()
}