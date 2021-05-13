package com.example.pokedex.data

import com.example.pokedex.data.network.PokedexApiService
import com.example.pokedex.data.network.PokemonDetailsResponse
import com.example.pokedex.domain.PokemonEntity
import com.example.pokedex.domain.PokemonRepository
import com.example.pokedex.domain.Result
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkPokemonRepository(
    val api: PokedexApiService
    ): PokemonRepository {

    override suspend fun getPokemonList(): Result<List<PokemonEntity>> = withContext(Dispatchers.IO) {
        try {
            val ids = api.fetchPokemonList().results.map { it.name }
            val pokemonListWithDetails = ids.map { id ->
                api.fetchPokemonDetails(id).toEntity()
            }
            Result.Success(pokemonListWithDetails)
        } catch (th: Exception) {
            Result.Error(th)
        }
    }


    override suspend fun getPokemonById(id: String): Result<PokemonEntity> = withContext(Dispatchers.IO) {
        try {
            val entity = api.fetchPokemonDetails(id).toEntity()
            Result.Success(entity)
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    private fun PokemonDetailsResponse.toEntity() =
        PokemonEntity(
            id = id,
            name = name,
            previewUrl = generateUrlFromId(id),
            abilities = abilities.map { it.ability.name })

    private fun generateUrlFromId(id: String): String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

}
