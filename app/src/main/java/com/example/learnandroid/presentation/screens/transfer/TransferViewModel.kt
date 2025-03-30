package com.example.learnandroid.presentation.screens.transfer

import androidx.lifecycle.viewModelScope
import com.example.learnandroid.domain.repository.CurrencyRepository
import com.example.learnandroid.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : BaseViewModel<TransferState, TransferEvent, TransferEffect>(TransferState()) {

    override fun onEvent(event: TransferEvent) {
        viewModelScope.launch {
            when (event) {
                is TransferEvent.CardSelected -> updateState { copy(fromCard = event.card) }

                is TransferEvent.ChooseCardClicked -> emitEffect(TransferEffect.NavigateToChooseCard)

                is TransferEvent.ChooseAnAccountClicked -> emitEffect(TransferEffect.NavigateToChooseAccount)

                is TransferEvent.GetCurrency -> {
                    val response = currencyRepository.getCurrency()
                    updateState { copy(currency = response.course) }
                }

                is TransferEvent.UpdateBuyInput -> {
                    updateState { copy(buy = event.buy) }
                    emitEffect(TransferEffect.UpdateBuyInput(state.value.buy))
                }

                is TransferEvent.UpdateSellInput -> {
                    updateState { copy(sell = event.sell) }
                    emitEffect(TransferEffect.UpdateSellInput(state.value.sell))
                }

                is TransferEvent.UpdateDescriptionInput -> updateState { copy(description = event.description) }

                is TransferEvent.ToCardSelected -> updateState { copy(toCard = event.card) }
            }
        }
    }
}