package com.example.pokedex.presentation.list.adapter

import com.example.pokedex.domain.PokemonEntity


interface DisplayableItem

data class PokemonItem(
        val id: String,
        val name: String,
        val image: String,
        val useRedColor: Boolean = false,
): DisplayableItem

data class HeaderItem(
        val text: String
): DisplayableItem

fun PokemonEntity.toItem() = PokemonItem(id, name, previewUrl)