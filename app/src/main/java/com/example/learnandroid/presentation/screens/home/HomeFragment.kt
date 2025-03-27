package com.example.learnandroid.presentation.screens.home

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.databinding.FragmentHomeBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.util.compressUri
import com.example.learnandroid.presentation.util.hide
import com.example.learnandroid.presentation.util.launchViewLifecycleOwnerScopeWithStartedState
import com.example.learnandroid.presentation.util.show
import com.example.learnandroid.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by activityViewModels()

    private val uploadImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                launchViewLifecycleOwnerScopeWithStartedState {
                    val compressedUri = compressUri(requireContext(), it)
                    viewModel.onEvent(HomeEvent.UploadImage(compressedUri))
                }
            }
        }

    private val imageAdapter by lazy {
        ImageAdapter { uri -> navigateToPreviewFragment(uri.toString()) }
    }

    override fun start() {
        setupRecyclerView()
        observeState()
        observeEffects()
    }

    override fun setupListeners() {
        binding.btnAddImage.setOnClickListener {
            viewModel.onEvent(HomeEvent.AddImageButtonClicked)
        }
        binding.imgAddImage.setOnClickListener {
            viewModel.onEvent(HomeEvent.AddImageButtonClicked)
        }
        binding.btnUpload.setOnClickListener {
            uploadImageLauncher.launch("image/*")
        }
        binding.imgUpload.setOnClickListener {
            uploadImageLauncher.launch("image/*")
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = imageAdapter
    }

    private fun navigateToPreviewFragment(uri: String) {
        val direction = HomeFragmentDirections.actionHomeFragmentToImagePreviewFragment(uri)
        findNavController().navigate(direction)
    }

    private fun observeState() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.state.collect { state ->
                imageAdapter.submitList(state.imageList)
                handleLoader(state.isUploading)
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

                    is HomeEffect.ShowToast -> {
                        requireContext().showToast(effect.text)
                    }
                }
            }
        }
    }

    private fun handleLoader(isUploading: Boolean?) {
        with(binding) {
            if (isUploading == true) {
                btnUpload.isEnabled = false
                imgUpload.isEnabled = false
                progressBar.show()
            } else {
                btnUpload.isEnabled = true
                imgUpload.isEnabled = true
                progressBar.hide()
            }
        }
    }
}
