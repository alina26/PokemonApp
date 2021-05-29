package com.example.pokedex.presentation.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.domain.PokemonRepository
import kotlinx.coroutines.launch
import com.example.pokedex.domain.Result
import com.example.pokedex.presentation.list.adapter.toItem

class PokemonListViewModel(private val repository: PokemonRepository) : ViewModel() {

    private val viewStateLiveData = MutableLiveData<PokemonListViewState>()
    fun viewState(): LiveData<PokemonListViewState> = viewStateLiveData

    fun fetch() {

        viewStateLiveData.value = PokemonListViewState.Loading

        viewModelScope.launch {
            viewStateLiveData.value = when (val result = repository.getPokemonList()) {
                is Result.Success -> {
                    val pokemonList = result.data
                    PokemonListViewState.Content(pokemonList.map { it.toItem() })
                }

                is Result.Error -> {
                    Log.d("ViewModel", "Error is", result.exception)
                    PokemonListViewState.Error("Error Message")
                }
            }
        }
    }
}