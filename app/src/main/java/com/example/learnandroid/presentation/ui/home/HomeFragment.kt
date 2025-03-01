package com.example.learnandroid.presentation.ui.home

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.learnandroid.databinding.FragmentHomeBinding
import com.example.learnandroid.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun start() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.randomRecipes.collect {
                Log.d("!!!", it.randomRecipes.recipes.toString())
            }
        }
    }

    override fun setUpListeners() {

    }
}