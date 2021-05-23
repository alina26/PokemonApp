package com.example.pokedex.presentation.details
import com.example.pokedex.domain.Result
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.di.Injector
import com.example.pokedex.domain.PokemonEntity
import com.example.pokedex.domain.PokemonRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val id: String,
    private val repository: PokemonRepository
): ViewModel() {

    private val viewStateLiveData = MutableLiveData<PokemonDetailsViewState>()

    fun viewState(): LiveData<PokemonDetailsViewState> = viewStateLiveData

    fun loadPokemon() {
        viewStateLiveData.value = PokemonDetailsViewState.Loading


        viewModelScope.launch {
            delay(2000)
            viewStateLiveData.value = when (val result = repository.getPokemonById(id)) {
                is Result.Success -> {
                    val pokemonEntity = result.data
                    createContentViewState(pokemonEntity)
                }
                is Result.Error -> {
                    Log.d("ViewModel", "Error: ", result.exception)
                    createErrorViewState("Failed to load pokemon with id=$id")
                }
            }
        }
    }

    private fun PokemonEntity.toContentViewState() = PokemonDetailsViewState.Content(
        name = name,
        imageUrl = previewUrl,
        abilities = abilities
    )

    private fun createContentViewState(pokemonEntity: PokemonEntity) =
        pokemonEntity.toContentViewState()

    private fun createErrorViewState(message: String) = PokemonDetailsViewState.Error(message)
}