package com.example.pokedex.domain

import io.reactivex.Single

interface PokemonRepository {
//    fun getPokemonList(): List<PokemonEntity>
//    fun addNewPokemon(pokemon: PokemonEntity)
    fun getPokemonList(): Single<List<PokemonEntity>>
    fun getPokemonById(id: String): Single<PokemonEntity>
}

interface RepositoryCallback<T> {
    fun onSuccess(data: T)
    fun onError(error: String)
}