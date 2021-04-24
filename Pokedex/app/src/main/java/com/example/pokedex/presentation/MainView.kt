package com.example.pokedex.presentation

import com.example.pokedex.presentation.list.adapter.DisplayableItem

interface MainView {
    fun showProgress()
    fun showData(items: List<DisplayableItem>)
    fun showError(errorMessage: String)
}