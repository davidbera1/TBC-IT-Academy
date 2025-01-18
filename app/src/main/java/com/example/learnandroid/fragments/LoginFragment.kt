package com.example.learnandroid.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentLoginBinding
import com.example.learnandroid.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun setUpListeners() {
        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLoginResult()
    }

    private fun validateFields(): Boolean {
        if (binding.etEmail.text.toString().isNotEmpty() && binding.etPassword.text.toString()
                .isNotEmpty()
        ) {
            return true
        } else {
            return false
        }
    }

    // only allowed email: eve.holt@reqres.in
    private fun login() {
        if (validateFields()) {
            viewModel.login(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.please_fill_both_fields),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun observeLoginResult() {
        lifecycleScope.launch {
            viewModel.loginResult.collect { boolean ->
                when (boolean) {
                    true -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.login_was_successful),
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.resetLoginResult()
                    }

                    false -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.login_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.resetLoginResult()
                    }

                    else -> {}
                }
            }
        }
    }
}