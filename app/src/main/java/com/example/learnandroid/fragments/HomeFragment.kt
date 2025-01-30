package com.example.learnandroid.fragments

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.learnandroid.databinding.FragmentHomeBinding
import com.example.learnandroid.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private var isCollected = true

    private val viewModel: HomeViewModel by viewModels()

    override fun setUpListeners() {
        binding.btnSave.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()

            viewModel.updateUser(firstName, lastName, email)
        }

        binding.btnRead.setOnClickListener {
            isCollected = false
            collectFlow()
        }
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userFlow.collectLatest { user ->
                if (!isCollected) {
                    binding.tvFirstName.text = user.firstName
                    binding.tvLastName.text = user.lastName
                    binding.tvEmail.text = user.email
                    isCollected = true
                }
            }
        }
    }
}
