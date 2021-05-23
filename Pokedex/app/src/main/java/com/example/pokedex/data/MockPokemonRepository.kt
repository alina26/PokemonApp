package com.example.pokedex.data

import com.example.pokedex.domain.PokemonEntity
import com.example.pokedex.domain.PokemonRepository
import com.example.pokedex.domain.Result
import java.lang.Exception

class MockPokemonRepository : PokemonRepository {

    private val items = mutableListOf<PokemonEntity>(
        PokemonEntity("1", "bulbasaur", generateUrlFromId(1)),
        PokemonEntity("2", "ivysaur", generateUrlFromId(2)),
        PokemonEntity("3", "venusaur", generateUrlFromId(3)),
        PokemonEntity("4", "charmander", generateUrlFromId(4)),
        PokemonEntity("5", "charmeleon", generateUrlFromId(5)),
        PokemonEntity("6", "charizard", generateUrlFromId(6)),
        PokemonEntity("7", "squirtle", generateUrlFromId(7)),
        PokemonEntity("8", "wartortle", generateUrlFromId(8)),
        PokemonEntity("9", "blastoise", generateUrlFromId(9)),
        PokemonEntity("10", "caterpie", generateUrlFromId(10)),
        )

    override suspend fun getPokemonList(): Result<List<PokemonEntity>> = Result.Success(items)

    override suspend fun getPokemonById(id: String): Result<PokemonEntity> {
        val pokemon = items.find { it.id == id }
        return if (pokemon != null) {
            Result.Success(pokemon)
        } else {
            Result.Error(Exception("Not found"))
        }
    }

    private fun generateUrlFromId(id: Int): String =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}