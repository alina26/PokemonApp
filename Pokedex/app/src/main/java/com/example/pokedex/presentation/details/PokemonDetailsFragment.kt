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

class PokemonDetailsFragment : Fragment(R.layout.fragment_pokemon_details) {

    private val args by navArgs<PokemonDetailsFragmentArgs>()
    private val viewModel: PokemonDetailsViewModel by viewModel { parametersOf(args.pokemonId) }
    private var binding: FragmentPokemonDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPokemonDetailsBinding.bind(view)
        viewModel.viewState().observe(viewLifecycleOwner) { state -> showViewState(state) }
        viewModel.loadPokemon()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showViewState(viewState: PokemonDetailsViewState) = binding?.apply {
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

    private fun showContent(state: PokemonDetailsViewState.Content) = binding?.apply {
        name.text = state.name
        abilities.text = state.abilities.joinToString(separator = ",") { it }
        Picasso.get().load(state.imageUrl).into(image)
    }
}