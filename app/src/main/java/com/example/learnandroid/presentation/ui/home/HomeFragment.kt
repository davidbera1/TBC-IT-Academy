package com.example.learnandroid.presentation.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentHomeBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.mapper.toRecipe
import com.example.learnandroid.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: RandomRecipesAdapter

    override fun start() {
        setUpRecyclerView()
        observeRandomRecipes()
        observeSearchResult()
    }

    override fun setUpListeners() {
        binding.imgSearch.setOnClickListener {
            val query = binding.etSearch.text.toString()
            if (query.isNotEmpty()) {
                viewModel.searchFoodByName(query)
            } else {
                requireContext().showToast(getString(R.string.search_query_is_empty))
            }
        }
    }

    private fun observeSearchResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchResult.collect { searchResult ->
                binding.progressBar.visibility = if (searchResult.loader) View.VISIBLE else View.GONE
                if (searchResult.error != null) {
                    requireContext().showToast(searchResult.error)
                }
                adapter.submitList(searchResult.search?.results?.map { it.toRecipe() })
            }
        }
    }

    private fun observeRandomRecipes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.randomRecipes.collect {
                binding.progressBar.visibility = if (it.loader) View.VISIBLE else View.GONE
                if (it.error != null) {
                    requireContext().showToast(it.error)
                }
                adapter.submitList(it.randomRecipes.recipes)
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = RandomRecipesAdapter(onItemClicked = {
            // open detailed info fragment
        })
        binding.recyclerView.adapter = adapter
    }
}