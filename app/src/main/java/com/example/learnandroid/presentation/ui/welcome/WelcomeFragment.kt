package com.example.learnandroid.presentation.ui.welcome

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.databinding.FragmentWelcomeBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.util.UiUtils
import com.example.learnandroid.presentation.util.launchViewLifecycleOwnerScopeWithStartedState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {

    private val viewModel: WelcomeViewModel by viewModels()

    @Inject
    lateinit var uiUtils: UiUtils

    override fun start() {
        viewModel.sendIntent(WelcomeIntent.GetUserSession)
        observeState()
        observeEffects()
    }

    override fun setUpListeners() {
        binding.btnRegister.setOnClickListener { viewModel.sendIntent(WelcomeIntent.RegisterButtonClicked) }

        binding.btnLogin.setOnClickListener { viewModel.sendIntent(WelcomeIntent.LoginButtonClicked) }
    }

    private fun navigateToRegisterFragment() {
        val direction = WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment()
        findNavController().navigate(direction)
    }

    private fun navigateToLoginFragment() {
        val direction = WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment()
        findNavController().navigate(direction)
    }

    private fun navigateToHomeFragment() {
        val direction = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment()
        findNavController().navigate(direction)
    }

    private fun observeState() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.state.collect { state ->
                uiUtils.handleLoader(
                    progressBar = binding.progressBar,
                    loadingView = binding.loadingView,
                    button = binding.btnRegister,
                    button2 = binding.btnLogin,
                    loader = state.isLoading
                )
            }
        }
    }

    private fun observeEffects() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.effects.collect { effect ->
                when (effect) {
                    WelcomeEffect.NavigateToHome -> navigateToHomeFragment()
                    WelcomeEffect.NavigateToLogin -> navigateToLoginFragment()
                    WelcomeEffect.NavigateToRegister -> navigateToRegisterFragment()
                }
            }
        }
    }
}