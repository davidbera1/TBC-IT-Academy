package com.example.learnandroid.presentation.ui.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentProfileBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.util.UiUtils
import com.example.learnandroid.presentation.util.launchViewLifecycleOwnerScopeWithStartedState
import com.example.learnandroid.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var uiUtils: UiUtils

    override fun start() {
        viewModel.sendIntent(ProfileIntent.ReadUserSession)
        observeState()
        observeEffects()
    }

    override fun setUpListeners() {
        binding.btnLogout.setOnClickListener { viewModel.sendIntent(ProfileIntent.LogoutButtonClicked) }
    }

    private fun navigateToLoginFragment() {
        val direction = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
        findNavController().navigate(direction)
    }

    private fun observeState() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.state.collect { state ->
                uiUtils.handleLoader(
                    progressBar = binding.progressBar,
                    loader = state.isLoading
                )
                binding.tvEmail.text = getString(R.string.welcome_email, state.email)
            }
        }
    }

    private fun observeEffects() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.effects.collect { effect ->
                when (effect) {
                    is ProfileEffect.ShowToast -> requireContext().showToast(effect.message)

                    is ProfileEffect.NavigateToLogin -> navigateToLoginFragment()
                }
            }
        }
    }
}