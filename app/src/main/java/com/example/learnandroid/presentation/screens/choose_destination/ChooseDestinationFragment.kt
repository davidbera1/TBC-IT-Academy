package com.example.learnandroid.presentation.screens.choose_destination

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.learnandroid.databinding.FragmentChooseDestinationBinding
import com.example.learnandroid.presentation.base.BaseBottomSheet
import com.example.learnandroid.presentation.extension.launchViewLifecycleOwnerScopeWithStartedState
import com.example.learnandroid.presentation.extension.showToast
import com.example.learnandroid.presentation.model.CardUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseDestinationFragment :
    BaseBottomSheet<FragmentChooseDestinationBinding>(FragmentChooseDestinationBinding::inflate) {

    private val viewModel: ChooseDestinationViewModel by viewModels()

    override fun setupListeners() {
        binding.btnContinue.setOnClickListener {
            checkAccountStatus()
        }
    }

    private fun checkAccountStatus() {
        val accountNumber = binding.etAccountNumber.text.toString()
        if (viewModel.validateAccount(accountNumber)) {
            launchViewLifecycleOwnerScopeWithStartedState {
                // if account status is success then return dummy card value to parent
                if (viewModel.checkAccountStatus(accountNumber)) {
                    navigateBackWithCardDetails(
                        card = CardUi(
                            id = 100,
                            accountName = "Dummy Card",
                            accountNumber = "GE12304188890012838012",
                            valuteType = CardUi.Currency.GEL,
                            cardType = CardUi.CardType.VISA,
                            balance = 9500,
                            cardLogo = null
                        )
                    )
                }
            }
        } else {
            requireContext().showToast("Account number is invalid")
        }
    }

    private fun navigateBackWithCardDetails(card: CardUi) {
        val result = Bundle().apply {
            putParcelable(SELECTED_OPTION_KEY, card)
        }
        parentFragmentManager.setFragmentResult(DESTINATION_CARD_DETAILS_KEY, result)
        dismiss()
    }

    companion object {
        const val DESTINATION_CARD_DETAILS_KEY = "destination_card_details"
        const val SELECTED_OPTION_KEY = "selected_option"
    }
}