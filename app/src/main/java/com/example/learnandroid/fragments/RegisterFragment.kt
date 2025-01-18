package com.example.learnandroid.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentRegisterBinding
import com.example.learnandroid.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun setUpListeners() {
        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeRegisterResult()
    }

    private fun validateFields(): Boolean {
        if (binding.etEmail.text.toString().isNotEmpty() &&
            binding.etUsername.text.toString().isNotEmpty() &&
            binding.etPassword.text.toString().isNotEmpty()
        ) {
            return true
        } else {
            return false
        }
    }

    // only allowed email: eve.holt@reqres.in
    private fun register() {
        if (validateFields()) {
            viewModel.register(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.please_fill_all_fields),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun observeRegisterResult() {
        lifecycleScope.launch {
            viewModel.registerResult.collect { boolean ->
                when (boolean) {
                    true -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.registration_was_successful),
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.resetRegisterResult()
                    }

                    false -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.registration_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.resetRegisterResult()
                    }

                    else -> {}
                }
            }
        }
    }
}