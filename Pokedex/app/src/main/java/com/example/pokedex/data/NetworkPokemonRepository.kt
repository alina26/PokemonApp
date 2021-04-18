package com.example.pokedex.data

import com.example.pokedex.data.network.PokedexApiService
import com.example.pokedex.data.network.PokemonDetailedResponse
import com.example.pokedex.data.network.PokemonListResponse
import com.example.pokedex.domain.PokemonEntity
import com.example.pokedex.domain.PokemonRepository
import com.example.pokedex.domain.RepositoryCallback
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkPokemonRepository(
    val api: PokedexApiService
    ): PokemonRepository {

    override fun getPokemonList(): Single<List<PokemonEntity>> {
        return api.fetchPokemonList()
            .flatMap { pokemonList ->
                Observable.fromIterable(pokemonList.results)
                    .flatMapSingle {
                        getPokemonById(it.name)
                    }.toList()
            }
    }


    override fun getPokemonById(id: String): Single<PokemonEntity> {
        return api.fetchPokemonInfo(id)
            .map { serverPokemon ->
                val abilities = serverPokemon.abilities.map { it.ability.name }

                PokemonEntity(
                    id = serverPokemon.id,
                    name = serverPokemon.name,
                    previewUrl = generateUrlFromId(serverPokemon.id),
                    abilities = abilities)
            }
    }

    fun generateUrlFromId(id: String): String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

}
