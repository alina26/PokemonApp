package com.example.pokedex.presentation.list

import com.example.pokedex.presentation.list.adapter.PokemonItem

sealed class PokemonListViewState {

    object Loading: PokemonListViewState()
    data class Error(val message: String): PokemonListViewState()
    data class Content(val items: List<PokemonItem>): PokemonListViewState()
}