package com.example.learnandroid.presentation.ui.home

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.learnandroid.databinding.FragmentHomeBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.util.hide
import com.example.learnandroid.presentation.util.launchViewLifecycleOwnerScopeWithStartedState
import com.example.learnandroid.presentation.util.show
import com.example.learnandroid.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val adapter by lazy { CategoryAdapter() }

    override fun start() {
        binding.recyclerView.adapter = adapter

        observeState()
        observeEffects()
    }

    override fun setupListeners() {
        binding.etSearch.addTextChangedListener {
            viewModel.onEvent(HomeEvent.UpdateSearchQuery(it.toString()))
        }
    }

    private fun observeState() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.state.collect { state ->
                handleLoader(state.isLoading)

                adapter.submitList(state.categories)
            }
        }
    }

    private fun observeEffects() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.effects.collect { effect ->
                when (effect) {
                    is HomeEffect.ShowToast -> requireContext().showToast(effect.message)
                }
            }
        }
    }

    private fun handleLoader(isLoading: Boolean) {
        with(binding) {
            if (isLoading) progressBar.show() else progressBar.hide()
        }
    }
}