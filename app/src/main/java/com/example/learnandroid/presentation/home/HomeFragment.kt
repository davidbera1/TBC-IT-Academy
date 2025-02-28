package com.example.learnandroid.presentation.home

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
        viewModel.getRandomRecipes(1)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.randomRecipes.collect {
                Log.d("!!!", it.toString())
            }
        }
    }

    override fun setUpListeners() {

    }
}