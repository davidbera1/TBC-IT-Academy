package com.example.learnandroid.presentation.ui.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnandroid.databinding.FragmentHomeBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.model.UserUi
import com.example.learnandroid.presentation.util.UiUtils
import com.example.learnandroid.presentation.util.hide
import com.example.learnandroid.presentation.util.launchViewLifecycleOwnerScopeWithStartedState
import com.example.learnandroid.presentation.util.show
import com.example.learnandroid.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val adapter: UsersAdapter by lazy { UsersAdapter() }

    @Inject
    lateinit var uiUtils: UiUtils

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
                uiUtils.handleLoader(
                    progressBar = binding.progressBar,
                    button = binding.btnProfile,
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
}