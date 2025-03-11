package com.example.learnandroid.presentation.ui.register

import android.os.Bundle
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
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
        handleRegisterResult()
    }

    override fun setUpListeners() {
        binding.btnRegister.setOnClickListener { register() }

        binding.imgBtnBack.setOnClickListener { findNavController().navigateUp() }
    }

    // only allowed email: eve.holt@reqres.in
    private fun register() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val repeatPassword = binding.etRepeatPassword.text.toString()

        viewModel.register(email = email, password = password, repeatPassword = repeatPassword)
    }

    private fun handleRegisterResult() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.registerState.collect { registerState ->

                uiUtils.handleLoader(binding.progressBar, binding.loadingView, registerState.loader)
                uiUtils.handleErrorMessage(requireContext(), registerState.errorMessage)
                uiUtils.handleAction(binding.btnRegister, registerState.action)

                if (registerState.registerResult == true) {
                    requireContext().showToast(getString(R.string.registration_was_successful))

                    val bundle = Bundle().apply {
                        putString("email", binding.etEmail.text.toString())
                        putString("password", binding.etPassword.text.toString())
                    }

                    setFragmentResult("credentials", bundle)

                    val direction =
                        RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                    findNavController().navigate(direction)
                }
            }
        }
    }
}