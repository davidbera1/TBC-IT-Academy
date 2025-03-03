package com.example.learnandroid.presentation.ui.favorites

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentFavoritesBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.common.RecipesAdapter
import com.example.learnandroid.presentation.ui.bottom_nav_bar_container.BottomNavBarContainerFragmentDirections
import com.example.learnandroid.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adapter: RecipesAdapter

    override fun start() {
        setUpRecyclerView()
        observeFavoriteRecipes()
    }

    private fun setUpRecyclerView() {
        adapter = RecipesAdapter(
            onItemClicked = { id ->
                val direction =
                    BottomNavBarContainerFragmentDirections.actionBottomNavBarContainerFragmentToRecipeDetailsBottomSheetFragment(
                        id
                    )
                findNavController().navigate(direction)
            },
            onItemLongClicked = { id ->
                viewModel.removeFavoriteId(id)
                requireContext().showToast(getString(R.string.removed_from_favorites))
            }
        )
        binding.recyclerView.adapter = adapter
    }

    private fun observeFavoriteRecipes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteRecipes.collect { favoriteRecipes ->
                binding.progressBar.visibility =
                    if (favoriteRecipes.loader) View.VISIBLE else View.GONE

                if (favoriteRecipes.error != null) {
                    requireContext().showToast(favoriteRecipes.error)
                }

                adapter.submitList(favoriteRecipes.favoriteRecipes)
            }
        }
    }
}