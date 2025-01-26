package com.example.learnandroid.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnandroid.adapter.UsersAdapter
import com.example.learnandroid.databinding.FragmentHomeBinding
import com.example.learnandroid.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: UsersAdapter

    override fun setUpListeners() {
        binding.btnProfile.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        adapter = UsersAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getUsers()
        observeGetUsersResult()
    }

    private fun observeGetUsersResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getUsersResult.collect { result ->

                if (result.loader == true) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }

                result.users?.data?.let { users ->
                    adapter.submitList(users.toList())
                }

                if (result.errorMessage != null) {
                    Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}