package com.example.learnandroid.presentation.ui.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnandroid.databinding.FragmentHomeBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.util.hide
import com.example.learnandroid.presentation.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val adapter: UsersAdapter by lazy { UsersAdapter() }

    override fun start() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        observePaging()
    }

    override fun setUpListeners() {
        binding.btnProfile.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            findNavController().navigate(direction)
        }
    }

    private fun observePaging() {
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
                    binding.progressBar.show()
                } else {
                    binding.progressBar.hide()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.usersPager.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}