package com.example.learnandroid.presentation.screens.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.databinding.FragmentHomeBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.util.launchViewLifecycleOwnerScopeWithStartedState

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun start() {

        observeEffects()
    }

    override fun setupListeners() {
        binding.btnAddImage.setOnClickListener { viewModel.onEvent(HomeEvent.AddImageButtonClicked) }
    }

    private fun navigateToImagePicker() {
        val direction =
            HomeFragmentDirections.actionHomeFragmentToImagePickerBottomSheetDialogFragment()
        findNavController().navigate(direction)
    }

    private fun observeEffects() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.effects.collect { effect ->
                when (effect) {
                    is HomeEffect.NavigateToImagePicker -> navigateToImagePicker()
                }
            }
        }
    }
}