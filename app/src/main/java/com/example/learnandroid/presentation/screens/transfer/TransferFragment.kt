package com.example.learnandroid.presentation.screens.transfer

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.example.learnandroid.databinding.FragmentTransferBinding
import com.example.learnandroid.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferFragment : BaseFragment<FragmentTransferBinding>(FragmentTransferBinding::inflate) {

    private val viewModel: TransferViewModel by viewModels()

    override fun setUpListeners() {
        binding.sellContainer.setOnClickListener { openSellInput() }
        binding.buyContainer.setOnClickListener { openBuyInput() }
    }

    private fun openSellInput() {
        binding.etSell.requestFocus()
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.etSell, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun openBuyInput() {
        binding.etBuy.requestFocus()
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.etBuy, InputMethodManager.SHOW_IMPLICIT)
    }
}