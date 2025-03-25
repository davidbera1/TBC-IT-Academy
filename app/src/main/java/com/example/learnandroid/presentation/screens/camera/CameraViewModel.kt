package com.example.learnandroid.presentation.screens.camera

import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.viewModelScope
import com.example.learnandroid.domain.use_case.TakePhotoUseCase
import com.example.learnandroid.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val takePhotoUseCase: TakePhotoUseCase
) : BaseViewModel<CameraState, CameraEvent, Nothing>(CameraState()) {

    override fun onEvent(event: CameraEvent) {
        when (event) {
            is CameraEvent.TakePhoto -> takePhoto(event.controller)
        }
    }

    private fun takePhoto(controller: LifecycleCameraController) {
        viewModelScope.launch {
            updateState { copy(isLoading = true, error = null) }

            val result = takePhotoUseCase(controller)

            result.onSuccess { uri ->
                updateState { copy(isLoading = false, photoUri = uri) }
            }.onFailure { error ->
                updateState { copy(isLoading = false, error = error.message) }
            }
        }
    }
}
