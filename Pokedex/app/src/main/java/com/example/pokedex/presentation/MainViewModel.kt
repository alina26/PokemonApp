package com.example.pokedex.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.data.MockPokemonRepository
import com.example.pokedex.domain.PokemonEntity
import com.example.pokedex.domain.PokemonRepository
import com.example.pokedex.presentation.adapter.DisplayableItem
import com.example.pokedex.presentation.adapter.HeaderItem
import com.example.pokedex.presentation.adapter.PokemonItem

class MainViewModel: ViewModel() {

    private val repository: PokemonRepository = MockPokemonRepository()

    private val _pokemonListLiveData = MutableLiveData<List<DisplayableItem>>()
    fun getPokemonList(): LiveData<List<DisplayableItem>> = _pokemonListLiveData

    fun loadData() {
        val pokemons = repository.getPokemonList()

        val pokemonItemList = pokemons.mapIndexed { index, pokemon ->
            val userColor = index % 4 == 0
            PokemonItem(pokemon.id, pokemon.name, pokemon.previewUrl, userColor)
        }

        val banner = HeaderItem("Banner")
        val newList = mutableListOf<DisplayableItem>()
        newList.add(banner)
        newList.addAll(pokemonItemList)


        _pokemonListLiveData.value = newList
    }

    private fun PokemonEntity.toItem(): PokemonItem =
        PokemonItem(id, name, previewUrl)

}