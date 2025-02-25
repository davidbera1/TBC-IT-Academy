package com.example.learnandroid.presentation.favorites

import androidx.fragment.app.viewModels
import com.example.learnandroid.databinding.FragmentFavoritesBinding
import com.example.learnandroid.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel: FavoritesViewModel by viewModels()

    override fun start() {

    }

    override fun setUpListeners() {

    }

}