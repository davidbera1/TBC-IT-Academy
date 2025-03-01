package com.example.learnandroid.presentation.ui.login

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentLoginBinding
import com.example.learnandroid.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun start() {
        observeLoginState()
    }

    override fun setUpListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.login(email, password)
        }
    }

    private fun observeLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                binding.btnLogin.isEnabled = !state.loader

                binding.progressBar.visibility = if (state.loader) View.VISIBLE else View.GONE

                state.result?.onSuccess {
                    val direction =
                        LoginFragmentDirections.actionLoginFragmentToBottomNavBarContainerFragment()
                    findNavController().navigate(direction)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.login_successful),
                        Toast.LENGTH_SHORT
                    ).show()
                }?.onFailure { error ->
                    Toast.makeText(
                        requireContext(),
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}