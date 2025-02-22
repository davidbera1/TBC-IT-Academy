package com.example.learnandroid.presentation.welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.databinding.FragmentWelcomeBinding
import com.example.learnandroid.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {

    private val viewModel: WelcomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkUserSession()
    }

    override fun setUpListeners() {
        binding.btnLogin.setOnClickListener {
            val direction = WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment()
            findNavController().navigate(direction)
        }

        binding.btnRegister.setOnClickListener {
            val direction = WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment()
            findNavController().navigate(direction)
        }
    }

    private fun checkUserSession() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    val direction = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment()
                    findNavController().navigate(direction)
                }
            }
        }
    }
}