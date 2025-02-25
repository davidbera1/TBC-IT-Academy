package com.example.learnandroid.presentation.register

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentRegisterBinding
import com.example.learnandroid.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun start() {
        observeRegisterState()
    }

    override fun setUpListeners() {
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.register(email, password)
        }
    }

    private fun observeRegisterState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.registerState.collect { state ->
                binding.btnRegister.isEnabled = !state.loader

                binding.progressBar.visibility = if (state.loader) View.VISIBLE else View.GONE

                state.result?.onSuccess {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.registration_successful),
                        Toast.LENGTH_SHORT
                    ).show()
                    parentFragmentManager.popBackStack()
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