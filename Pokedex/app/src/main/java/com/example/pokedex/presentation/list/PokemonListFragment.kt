package com.example.pokedex.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokemonListBinding
import com.example.pokedex.presentation.list.adapter.DisplayableItem
import com.example.pokedex.presentation.list.adapter.PokemonListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {

    private val viewModel: PokemonListViewModel by viewModel()
    private var adapter: PokemonListAdapter? = null
    private val viewBinding: FragmentPokemonListBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initViewModel()
        viewModel.fetch()
    }

    private fun initViewModel() {

        viewModel.viewState().observe(viewLifecycleOwner) { state -> showViewState(state) }
    }

    private fun showViewState(state: PokemonListViewState) {
        val progressView = viewBinding.animationLoader
        when (state) {
            is PokemonListViewState.Loading -> {
                progressView.isVisible = true
            }
            is PokemonListViewState.Error -> {
                progressView.isVisible = false
                showError(state.message)
            }
            is PokemonListViewState.Content -> {
                progressView.isVisible = false
                showData(state.items)
            }
        }
    }

    private fun initRecyclerView() {
        adapter = PokemonListAdapter(
            onItemClicked = { id ->
                val action = PokemonListFragmentDirections.actionPokemonListToPokemonDetails(id)
                findNavController().navigate(action)
            }
        )

        val manager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        viewBinding.recyclerView.apply {
            layoutManager = manager
            adapter = this@PokemonListFragment.adapter
        }

    }


    private fun showData(items: List<DisplayableItem>) {
        adapter?.submitList(items)
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }
}