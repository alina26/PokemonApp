package com.example.pokedex.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.data.NetworkPokemonRepository
import com.example.pokedex.data.network.createPokedexApiService
import com.example.pokedex.domain.PokemonRepository
import com.example.pokedex.presentation.list.adapter.DisplayableItem
import com.example.pokedex.presentation.list.adapter.toItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViwModel: ViewModel(){

    private val repository: PokemonRepository = NetworkPokemonRepository(
        api = createPokedexApiService()
    )

    private var disposable: Disposable? = null

    private val _pokemonListLiveData = MutableLiveData<List<DisplayableItem>>()
    fun getPokemonList(): LiveData<List<DisplayableItem>> = _pokemonListLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    fun loading(): LiveData<Boolean> = _loadingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    fun error(): LiveData<String> = _errorLiveData

    fun loadData() {
        _loadingLiveData.value = true

        disposable = repository.getPokemonList()
            .map { items -> items.map { it.toItem() } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _pokemonListLiveData.postValue(it)
                }, {
                    Log.d("ViewModel", "Error is", it)
                    _errorLiveData.postValue("Failed to load data")
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

}