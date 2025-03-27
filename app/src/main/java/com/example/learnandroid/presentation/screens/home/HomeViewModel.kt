package com.example.learnandroid.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.example.learnandroid.domain.use_case.GetImagesUseCase
import com.example.learnandroid.domain.use_case.UploadImageUseCase
import com.example.learnandroid.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase,
    private val getImagesUseCase: GetImagesUseCase
) : BaseViewModel<HomeState, HomeEvent, HomeEffect>(HomeState()) {

    init {
        viewModelScope.launch {
            val result = getImagesUseCase()
            result.onSuccess { uris ->
                updateState { copy(imageList = imageList + uris) }
            }.onFailure {
                emitEffect(HomeEffect.ShowToast("Failed to load images from Firebase"))
            }
        }
    }

    override fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.AddImageButtonClicked -> {
                    emitEffect(HomeEffect.NavigateToImagePicker)
                }
                is HomeEvent.ImageAdded -> {
                    updateState { copy(imageList = imageList + event.uri) }
                }
                is HomeEvent.UploadImage -> {
                    updateState { copy(isUploading = true) }

                    val result = uploadImageUseCase(event.uri)

                    result.onSuccess {
                        updateState { copy(isUploading = false) }
                        emitEffect(HomeEffect.ShowToast("Upload finished"))
                    }.onFailure {
                        updateState { copy(isUploading = false) }
                        emitEffect(HomeEffect.ShowToast("Upload failed"))
                    }
                }
                is HomeEvent.UploadSucceeded -> {
                    updateState { copy(isUploading = false) }
                    emitEffect(HomeEffect.ShowToast("Upload finished"))
                }
                is HomeEvent.UploadFailed -> {
                    updateState { copy(isUploading = false) }
                    emitEffect(HomeEffect.ShowToast("Upload failed"))
                }
            }
        }
    }
}
