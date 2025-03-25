package com.example.learnandroid.presentation.screens.camera

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.databinding.FragmentCameraBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.util.launchViewLifecycleOwnerScopeWithStartedState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CameraFragment : BaseFragment<FragmentCameraBinding>(FragmentCameraBinding::inflate) {

    private val viewModel: CameraViewModel by viewModels()
    private lateinit var cameraController: LifecycleCameraController

    private val cameraPermission = Manifest.permission.CAMERA
    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                setupCamera()
            } else {
                findNavController().popBackStack()
            }
        }

    override fun start() {
        if (ContextCompat.checkSelfPermission(requireContext(), cameraPermission)
            == PackageManager.PERMISSION_GRANTED
        ) {
            setupCamera()
        } else {
            permissionRequest.launch(cameraPermission)
        }

        observeState()
    }

    override fun setupListeners() {
        binding.btnCapture.setOnClickListener {
            viewModel.onEvent(CameraEvent.TakePhoto(cameraController))
        }
    }

    private fun setupCamera() {
        cameraController = LifecycleCameraController(requireContext())
        cameraController.bindToLifecycle(viewLifecycleOwner)
        binding.previewView.controller = cameraController
    }

    private fun observeState() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.state.collectLatest { state ->
                state.photoUri?.let { uri ->
                    findNavController().previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("captured_image_uri", uri)

                    findNavController().popBackStack()
                }
            }
        }
    }
}
