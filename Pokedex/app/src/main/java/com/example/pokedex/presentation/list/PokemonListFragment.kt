package com.example.pokedex.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.pokedex.R
import com.example.pokedex.presentation.Navigation
import com.example.pokedex.presentation.list.adapter.DisplayableItem
import com.example.pokedex.presentation.list.adapter.PokemonListAdapter
import com.example.pokedex.domain.Result

class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {

    private val viewModel = PokemonListViewModel()
    private var adapter: PokemonListAdapter? = null
    private val navigation: Navigation? by lazy { (activity as? Navigation) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressView = view.findViewById<LottieAnimationView>(R.id.animationLoader)
        initRecyclerView()

        viewModel.viewState().observe(viewLifecycleOwner){ state ->
            when(state){
                is PokemonListViewState.Loading ->{
                    progressView.isVisible = true
                    showProgress()
                }
                is PokemonListViewState.Error -> {
                    progressView.isVisible = false
                    showError(state.message)
                }
                is PokemonListViewState.Data -> {
                    progressView.isVisible = false
                    showData(state.items)
                }
            }
        }
        viewModel.loadData()
    }

    private fun initRecyclerView() {

        adapter = PokemonListAdapter(
            onItemClicked = { id ->
                navigation?.openPokemonDetails(id)
            }
        )

       val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)

        val manager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        recyclerView?.layoutManager = manager
        recyclerView?.adapter = adapter
    }

   private fun showProgress() {
        Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()

    }

   private fun showData(items: List<DisplayableItem>) {
        adapter?.setPokemonList(items)
    }

    private fun showError(errorMessage: String) {

    }
}