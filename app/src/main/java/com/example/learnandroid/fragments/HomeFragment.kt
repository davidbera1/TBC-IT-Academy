package com.example.learnandroid.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.learnandroid.R
import com.example.learnandroid.adapter.UserAdapter
import com.example.learnandroid.databinding.FragmentHomeBinding
import com.example.learnandroid.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                    return HomeViewModel(requireActivity().application) as T
                }
                throw IllegalArgumentException("")
            }
        }
    }

    private val userAdapter = UserAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        observeUsers()
        observeLoader()

        if(userHasInternetAccess(requireContext())) {
            binding.tvOnlineStatus.text = getString(R.string.you_re_online)
            homeViewModel.refreshUsers()
        } else {
            binding.tvOnlineStatus.text = getString(R.string.you_re_offline)
        }

    }

    private fun setUpRecyclerView() {
        binding.recyclerView.adapter = userAdapter
    }

    private fun userHasInternetAccess(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun observeUsers() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.users.collectLatest { users ->
                userAdapter.submitList(users)
            }
        }
    }

    private fun observeLoader() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.isLoading.collectLatest { loading ->
                binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            }
        }
    }
}
