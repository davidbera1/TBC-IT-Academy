package com.example.learnandroid.presentation.screens.image_preview

import android.net.Uri
import androidx.navigation.fragment.navArgs
import com.example.learnandroid.databinding.FragmentImagePreviewBinding
import com.example.learnandroid.presentation.base.BaseFragment

class ImagePreviewFragment :
    BaseFragment<FragmentImagePreviewBinding>(FragmentImagePreviewBinding::inflate) {

    private val args: ImagePreviewFragmentArgs by navArgs()

    override fun start() {
        val imageUri = Uri.parse(args.imageUri)
        binding.imgPhoto.setImageURI(imageUri)
    }
}