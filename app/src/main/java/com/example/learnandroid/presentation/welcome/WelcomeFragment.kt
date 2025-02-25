package com.example.learnandroid.presentation.welcome

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.data.datastore.LanguagePreference
import com.example.learnandroid.databinding.FragmentWelcomeBinding
import com.example.learnandroid.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {

    @Inject
    lateinit var languagePreference: LanguagePreference

    private val viewModel: WelcomeViewModel by viewModels()

    override fun start() {
        checkUserSession()
        observeLanguage()
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

        binding.imgLanguage.setOnClickListener {
            changeLanguage()
        }
    }

    private fun changeLanguage() {
        val newLanguage = if (viewModel.language.value == "en") "ka" else "en"
        viewModel.saveLanguage(newLanguage)
        requireActivity().recreate()
    }

    private fun observeLanguage() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.language.collect { language ->
                if (language == "ka") {
                    binding.imgLanguage.setImageResource(R.drawable.georgia)
                } else if (language == "en") {
                    binding.imgLanguage.setImageResource(R.drawable.usa)
                }
            }
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