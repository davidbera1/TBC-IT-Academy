package com.example.learnandroid.presentation.screens.home

import android.net.Uri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnandroid.databinding.FragmentHomeBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.util.launchViewLifecycleOwnerScopeWithStartedState

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by activityViewModels()

    private val imageAdapter by lazy {
        ImageAdapter { uri ->
            val direction =
                HomeFragmentDirections.actionHomeFragmentToImagePreviewFragment(uri.toString())
            findNavController().navigate(direction)
        }
    }

    override fun start() {
        setupRecyclerView()
        observeState()
        observeEffects()
        checkCapturedImage()
    }

    override fun setupListeners() {
        binding.btnAddImage.setOnClickListener {
            viewModel.onEvent(HomeEvent.AddImageButtonClicked)
        }

        binding.imgAddImage.setOnClickListener {
            viewModel.onEvent(HomeEvent.AddImageButtonClicked)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = imageAdapter
        }
    }

    private fun observeState() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.state.collect { state ->
                imageAdapter.submitList(state.imageList)
            }
        }
    }

    private fun observeEffects() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.effects.collect { effect ->
                when (effect) {
                    is HomeEffect.NavigateToImagePicker -> {
                        val direction =
                            HomeFragmentDirections.actionHomeFragmentToImagePickerBottomSheetDialogFragment()
                        findNavController().navigate(direction)
                    }
                }
            }
        }
    }

    private fun checkCapturedImage() {
        val uri = findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.remove<Uri>("captured_image_uri")

        uri?.let {
            viewModel.onEvent(HomeEvent.ImageAdded(it))
        }
    }
}
