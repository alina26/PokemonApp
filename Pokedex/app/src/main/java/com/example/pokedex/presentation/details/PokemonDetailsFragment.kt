package com.example.pokedex.presentation.details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokemonDetailsBinding
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import by.kirich1409.viewbindingdelegate.viewBinding

class PokemonDetailsFragment : Fragment(R.layout.fragment_pokemon_details) {

    private val navArgs by navArgs<PokemonDetailsFragmentArgs>()
    private val viewModel: PokemonDetailsViewModel by viewModel { parametersOf(navArgs.pokemonId) }
    private val viewBinding: FragmentPokemonDetailsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState().observe(viewLifecycleOwner) { state -> showViewState(state) }
        viewModel.loadPokemon()
    }

    private fun showViewState(viewState: PokemonDetailsViewState) = viewBinding.apply {
        when (viewState) {
            PokemonDetailsViewState.Loading -> {
                animationView.isVisible = true
                contentGroup.isVisible = false
                errorView.isVisible = false
            }
            is PokemonDetailsViewState.Content -> {
                animationView.isVisible = false
                contentGroup.isVisible = true
                errorView.isVisible = false
                showContent(viewState)
            }
            is PokemonDetailsViewState.Error -> {
                animationView.isVisible = false
                contentGroup.isVisible = false
                errorView.isVisible = true
            }
        }

    }

    private fun showContent(state: PokemonDetailsViewState.Content) = viewBinding.apply {
        name.text = state.name.capitalize()
        abilities.text = state.abilities.map { it.capitalize() }.joinToString(separator = ", ") { it }
        Picasso.get().load(state.imageUrl).into(image)
    }
}