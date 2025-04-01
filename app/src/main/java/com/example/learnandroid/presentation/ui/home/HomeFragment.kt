package com.example.learnandroid.presentation.ui.home

import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnandroid.databinding.FragmentHomeBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.extensions.hide
import com.example.learnandroid.presentation.extensions.launchViewLifecycleOwnerScopeWithStartedState
import com.example.learnandroid.presentation.extensions.show
import com.example.learnandroid.presentation.extensions.showToast
import com.example.learnandroid.presentation.model.UserUi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val adapter: UsersAdapter by lazy { UsersAdapter() }

    override fun start() {
        viewModel.onEvent(HomeEvent.RefreshUsers)
        setUpRecyclerView()
        observeState()
        observeEffect()
        observePagingState()
    }

    override fun setUpListeners() {
        binding.btnProfile.setOnClickListener { viewModel.onEvent(HomeEvent.ProfileButtonClicked) }
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }


    private fun navigateToProfile() {
        val direction = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
        findNavController().navigate(direction)
    }

    private fun observeState() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.state.collect { state ->
                handleLoader(
                    progressBar = binding.progressBar,
                    loader = state.isLoading
                )

                collectPagingDataAndSubmit(state.pagingData)
            }
        }
    }

    private fun collectPagingDataAndSubmit(pagingData: Flow<PagingData<UserUi>>) {
        launchViewLifecycleOwnerScopeWithStartedState {
            pagingData.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeEffect() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.effects.collect { effect ->
                when (effect) {
                    is HomeEffect.ShowToast -> requireContext().showToast(effect.message)
                    is HomeEffect.NavigateToProfile -> navigateToProfile()
                }
            }
        }
    }

    private fun observePagingState() {
        launchViewLifecycleOwnerScopeWithStartedState {
            adapter.loadStateFlow.collect { loadState ->
                if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
                    binding.progressBar.show()
                } else {
                    binding.progressBar.hide()
                }
            }
        }
    }

    private fun handleLoader(
        progressBar: ProgressBar,
        loader: Boolean?,
    ) {
        if (loader == false) {
            progressBar.show()

        } else if (loader == false) {
            progressBar.hide()
        }
    }
}