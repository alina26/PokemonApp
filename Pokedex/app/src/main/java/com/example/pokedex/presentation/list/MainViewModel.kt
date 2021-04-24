package com.example.pokedex.presentation.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.data.NetworkPokemonRepository
import com.example.pokedex.data.network.createPokedexApiService
import com.example.pokedex.di.Injector
import com.example.pokedex.domain.PokemonEntity
import com.example.pokedex.domain.PokemonRepository
import com.example.pokedex.presentation.list.adapter.DisplayableItem
import com.example.pokedex.presentation.list.adapter.HeaderItem
import com.example.pokedex.presentation.list.adapter.PokemonItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel: ViewModel() {

    private val repository = Injector.providePokemonRepository()

    private var disposable: Disposable? = null

    private val _pokemonListLiveData = MutableLiveData<List<DisplayableItem>>()
    fun getPokemonList(): LiveData<List<DisplayableItem>> = _pokemonListLiveData

    fun loadData() {
        //val pokemons = repository.getPokemonList()

        disposable = repository.getPokemonList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    showData(it)
                }, {
                    Log.d("ViewModel", "Error is", it)
                }
            )
    }

    private fun showData(pokemons: List<PokemonEntity>) {
        val pokemonItemList = pokemons.mapIndexed { index, pokemon ->
            val userColor = index % 4 == 0
            PokemonItem(pokemon.id, pokemon.name, pokemon.previewUrl, userColor)
        }

        val banner = HeaderItem("Items")
        val newList = mutableListOf<DisplayableItem>()
        newList.add(banner)
        newList.addAll(pokemonItemList)


        _pokemonListLiveData.postValue(newList)
    }

    private fun PokemonEntity.toItem(): PokemonItem =
        PokemonItem(id, name, previewUrl)

}