package com.example.learnandroid.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentLoginBinding
import com.example.learnandroid.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun setUpListeners() {
        binding.btnLogin.setOnClickListener { login() }

        binding.etEmail.doAfterTextChanged { checkLoginButtonState() }

        binding.etPassword.doAfterTextChanged { checkLoginButtonState() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("credentials") { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")
            binding.etEmail.setText(email)
            binding.etPassword.setText(password)
        }

        observeLoginResult()
    }

    // only allowed email: eve.holt@reqres.in
    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        viewModel.login(
            email = email,
            password = password
        )

        if (binding.cbRememberMe.isChecked) {
            observeTokenAndSaveUserSession(email = email)
        } else {
            saveUserSession(isLoggedIn = false, email = email, token = "")
        }
    }

    private fun observeTokenAndSaveUserSession(email: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginResult.collect { result ->
                if (!result.token.isNullOrEmpty()) {
                    saveUserSession(isLoggedIn = true, email = email, token = result.token)
                }
            }
        }
    }

    private fun saveUserSession(isLoggedIn: Boolean, email: String, token: String) {
        val editor = getUserSessionSharedPreferences().edit()

        editor.apply {
            putBoolean("isLoggedIn", isLoggedIn)
            putString("email", email)
            putString("token", token)
            apply()
        }
    }

    private fun observeLoginResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginResult.collect { result ->

                if (result.loader == true) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnLogin.isEnabled = false
                    binding.btnLogin.setBackgroundResource(R.drawable.gray_button_background)
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.isEnabled = true
                    binding.btnLogin.setBackgroundResource(R.drawable.purple_button_background)
                }

                if (result.loginResult == true) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.login_was_successful),
                        Toast.LENGTH_SHORT
                    ).show()

                    val direction = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(findNavController().graph.id, true)
                        .build()

                    findNavController().navigate(direction, navOptions = navOptions)

                } else if (result.loginResult == false) {
                    Toast.makeText(
                        requireContext(),
                        result.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun checkLoginButtonState() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty() &&
            email.length >= 10 && email.contains("@")
        ) {
            binding.btnLogin.isEnabled = true
            binding.btnLogin.setBackgroundResource(R.drawable.purple_button_background)
        } else {
            binding.btnLogin.isEnabled = false
            binding.btnLogin.setBackgroundResource(R.drawable.gray_button_background)
        }
    }
}