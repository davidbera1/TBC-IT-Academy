package com.example.learnandroid.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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

        binding.imgBtnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeRegisterResult()
    }

    private fun validateFields(): Boolean {
        return binding.etEmail.text.toString().isNotEmpty() &&
                binding.etPassword.text.toString().isNotEmpty() &&
                binding.etRepeatPassword.text.toString().isNotEmpty() &&
                binding.etPassword.text.toString() == binding.etRepeatPassword.text.toString()
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
                getString(R.string.please_fill_all_fields_correctly),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun observeRegisterResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.registerResult.collect { boolean ->
                when (boolean) {
                    true -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.registration_was_successful),
                            Toast.LENGTH_SHORT
                        ).show()

                        viewModel.resetRegisterResult()

                        val bundle = Bundle().apply {
                            putString("email", binding.etEmail.text.toString())
                            putString("password", binding.etPassword.text.toString())
                        }

                        setFragmentResult("credentials", bundle)

                        val direction =
                            RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                        val navOptions = androidx.navigation.NavOptions.Builder()
                            .setPopUpTo(findNavController().graph.id, true)
                            .build()

                        findNavController().navigate(direction, navOptions = navOptions)
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