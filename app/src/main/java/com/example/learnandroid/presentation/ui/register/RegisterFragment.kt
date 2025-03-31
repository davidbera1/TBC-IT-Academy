package com.example.learnandroid.presentation.ui.register

import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.databinding.FragmentRegisterBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.util.UiUtils
import com.example.learnandroid.presentation.util.launchViewLifecycleOwnerScopeWithStartedState
import com.example.learnandroid.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    @Inject
    lateinit var uiUtils: UiUtils

    override fun start() {
        observeUiFields()
        observeState()
        observeEffects()
    }

    override fun setUpListeners() {
        binding.btnRegister.setOnClickListener { viewModel.onEvent(RegisterEvent.RegisterButtonClicked) }

        binding.imgBtnBack.setOnClickListener { viewModel.onEvent(RegisterEvent.BackButtonClicked) }
    }

    private fun navigateToLoginFragment() {
        val direction = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(direction)
    }

    private fun observeUiFields() {
        binding.etEmail.addTextChangedListener {
            viewModel.onEvent(RegisterEvent.SendUpdatedEmail(it.toString()))
        }
        binding.etPassword.addTextChangedListener {
            viewModel.onEvent(RegisterEvent.SendUpdatedPassword(password = it.toString()))
        }

        binding.etRepeatPassword.addTextChangedListener {
            viewModel.onEvent(
                RegisterEvent.SendUpdatedRepeatPassword(repeatPassword = it.toString())
            )
        }
    }

    private fun observeState() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.state.collect { state ->
                uiUtils.handleLoader(
                    binding.progressBar,
                    binding.loadingView,
                    binding.btnRegister,
                    state.isLoading
                )
            }
        }
    }

    private fun observeEffects() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.effects.collect { effects ->
                when (effects) {
                    is RegisterEffect.ShowToast -> requireContext().showToast(effects.message)

                    is RegisterEffect.NavigateToLogin -> {
                        val bundle = Bundle().apply {
                            putString("email", binding.etEmail.text.toString())
                            putString("password", binding.etPassword.text.toString())
                        }
                        setFragmentResult(REQUEST_KEY, bundle)

                        navigateToLoginFragment()
                    }

                    is RegisterEffect.NavigateToHome -> findNavController().navigateUp()
                }
            }
        }
    }

    companion object {
        const val REQUEST_KEY = "credentials"
    }
}