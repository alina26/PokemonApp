package com.example.pokedex.di

import com.example.pokedex.data.NetworkPokemonRepository
import com.example.pokedex.data.network.PokedexApiService
import com.example.pokedex.domain.PokemonRepository
import com.example.pokedex.presentation.details.PokemonDetailsViewModel
import com.example.pokedex.presentation.list.PokemonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<PokedexApiService> { createPokedexApiService() }
    single<PokemonRepository> { NetworkPokemonRepository(get()) }

    viewModel { PokemonListViewModel(get()) }
    viewModel { (id: String) -> PokemonDetailsViewModel(id, get()) }
}

private fun createPokedexApiService(): PokedexApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(PokedexApiService::class.java)
}