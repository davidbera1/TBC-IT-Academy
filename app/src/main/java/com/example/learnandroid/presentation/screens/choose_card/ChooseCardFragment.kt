package com.example.learnandroid.presentation.screens.choose_card

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.learnandroid.databinding.FragmentChooseCardBinding
import com.example.learnandroid.presentation.base.BaseBottomSheet
import com.example.learnandroid.presentation.extension.hide
import com.example.learnandroid.presentation.extension.launchViewLifecycleOwnerScopeWithStartedState
import com.example.learnandroid.presentation.extension.show
import com.example.learnandroid.presentation.extension.showToast
import com.example.learnandroid.presentation.model.CardUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseCardFragment :
    BaseBottomSheet<FragmentChooseCardBinding>(FragmentChooseCardBinding::inflate) {

    private val viewModel: ChooseCardViewModel by viewModels()
    private val adapter by lazy { CardsAdapter(onItemClicked = {
        viewModel.onEvent(ChooseCardEvent.OnCardClicked(it))
    }) }

    override fun start() {
        observeState()
        observeEffects()
        binding.recyclerView.adapter = adapter
    }

    private fun observeState() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.state.collect { state ->
                handleLoader(state.isLoading)
                adapter.submitList(state.cards)
            }
        }
    }

    private fun observeEffects() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.effects.collect { effect ->
                when (effect) {
                    is ChooseCardEffect.NavigateBack -> dismiss()

                    is ChooseCardEffect.NavigateBackWithCardDetails -> {
                        navigateBackWithCardDetails(effect.card)
                    }

                    is ChooseCardEffect.ShowToast -> requireContext().showToast(effect.message)
                }
            }
        }
    }

    private fun navigateBackWithCardDetails(card: CardUi) {
        val result = Bundle().apply {
            putParcelable("selected_option", card)
        }
        parentFragmentManager.setFragmentResult("card_details", result)
        dismiss()
    }

    private fun handleLoader(isLoading: Boolean?) {
        with(binding.progressBar) {
            if (isLoading == true) show() else hide()
        }
    }
}