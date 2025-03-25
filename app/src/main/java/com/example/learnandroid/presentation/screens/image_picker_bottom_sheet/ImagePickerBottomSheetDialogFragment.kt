package com.example.learnandroid.presentation.screens.image_picker_bottom_sheet

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.databinding.FragmentImagePickerBottomSheetDialogBinding
import com.example.learnandroid.presentation.base.BaseBottomSheetDialogFragment
import com.example.learnandroid.presentation.screens.home.HomeEvent
import com.example.learnandroid.presentation.screens.home.HomeViewModel
import com.example.learnandroid.presentation.util.compressUri
import com.example.learnandroid.presentation.util.launchViewLifecycleOwnerScopeWithStartedState

class ImagePickerBottomSheetDialogFragment :
    BaseBottomSheetDialogFragment<FragmentImagePickerBottomSheetDialogBinding>(
        FragmentImagePickerBottomSheetDialogBinding::inflate
    ) {

    private val viewModel: HomeViewModel by activityViewModels()
    private val galleryPickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                launchViewLifecycleOwnerScopeWithStartedState {
                    val compressedUri = compressUri(requireContext(), it)
                    viewModel.onEvent(HomeEvent.ImageAdded(compressedUri))
                    dismiss()
                }
            }
        }

    override fun setupListeners() {
        binding.btnTakePhoto.setOnClickListener { navigateToCameraFragment() }
        binding.imgCamera.setOnClickListener { navigateToCameraFragment() }

        binding.btnChooseFromGallery.setOnClickListener { galleryPickerLauncher.launch("image/*") }
        binding.imgGallery.setOnClickListener { galleryPickerLauncher.launch("image/*") }
    }

    private fun navigateToCameraFragment() {
        dismiss()
        val direction =
            ImagePickerBottomSheetDialogFragmentDirections.actionImagePickerBottomSheetDialogFragmentToCameraFragment()
        findNavController().navigate(direction)
    }
}
