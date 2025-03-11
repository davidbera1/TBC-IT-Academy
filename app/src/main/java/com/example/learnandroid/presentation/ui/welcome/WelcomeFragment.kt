package com.example.learnandroid.presentation.ui.welcome

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

    override fun start() {
        checkSavedUserSession()
    }

    override fun setUpListeners() {
        binding.btnRegister.setOnClickListener {
            val direction = WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment()
            findNavController().navigate(direction)
        }

        binding.btnLogin.setOnClickListener {
            val direction = WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment()
            findNavController().navigate(direction)
        }
    }

    private fun checkSavedUserSession() {
        val flow = viewModel.getUserSession()
        viewLifecycleOwner.lifecycleScope.launch {
            flow.collect { userSession ->
                if (userSession?.isLoggedIn == true) {
                    val direction = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment()
                    findNavController().navigate(direction)
                }
            }
        }
    }
}