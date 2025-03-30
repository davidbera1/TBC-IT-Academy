package com.example.learnandroid.presentation.screens.transfer

import android.content.Context
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentTransferBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.extension.hide
import com.example.learnandroid.presentation.extension.launchViewLifecycleOwnerScopeWithStartedState
import com.example.learnandroid.presentation.extension.show
import com.example.learnandroid.presentation.model.CardUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferFragment : BaseFragment<FragmentTransferBinding>(FragmentTransferBinding::inflate) {

    private val viewModel: TransferViewModel by viewModels()
    private var currency: Double = 1.0
    private var sellTextWatcher: TextWatcher? = null
    private var buyTextWatcher: TextWatcher? = null

    override fun start() {
        observeState()
        observeEffects()
    }

    override fun setUpListeners() {
        binding.sellContainer.setOnClickListener { openSellInput() }

        binding.buyContainer.setOnClickListener { openBuyInput() }

        binding.fromContainer.setOnClickListener { viewModel.onEvent(TransferEvent.ChooseCardClicked) }

        binding.toContainer.setOnClickListener { viewModel.onEvent(TransferEvent.ChooseAnAccountClicked) }

        setUpSellChangedListener()

        setUpBuyChangedListener()
    }

    private fun observeState() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.state.collect { state ->
                setUpFromCardUi(state.fromCard)
                setUpToCardUi(state.toCard)

                currency = state.currency
                binding.tvCurrencyInfo.text = ""

                if (state.fromCard != null && state.toCard != null) {
                    val fromCurrency = handleCurrency(state.fromCard.valuteType)
                    val toCurrency = handleCurrency(state.toCard.valuteType)
                    binding.tvCurrencyInfo.text = "1$fromCurrency = ${1*currency}$toCurrency"
                    // valutes match, only one field is needed
                    if (state.fromCard.valuteType == state.toCard.valuteType) {
                        enableSellAndDescriptionInputs(state.fromCard.valuteType)
                    } else { // valutes don't match and all fields enabled
                        viewModel.onEvent(TransferEvent.GetCurrency)
                        enableAllFields(state.fromCard.valuteType, state.toCard.valuteType)
                    }
                }
            }
        }
    }

    private fun observeEffects() {
        launchViewLifecycleOwnerScopeWithStartedState {
            viewModel.effects.collect { effect ->
                when (effect) {
                    is TransferEffect.NavigateToChooseCard -> navigateToChooseCard()

                    is TransferEffect.NavigateToChooseAccount -> navigateToChooseDestination()

                    is TransferEffect.UpdateBuyInput -> {
                        binding.etBuy.removeTextChangedListener(buyTextWatcher)

                        val formattedBuy = String.format("%.2f", effect.buy)
                        binding.etBuy.setText(formattedBuy)

                        buyTextWatcher = binding.etBuy.addTextChangedListener { editable ->
                            val buyText = editable?.toString().orEmpty()
                            val buyValue = buyText.toDoubleOrNull() ?: 0.0
                            val sellValue = buyValue * currency
                            viewModel.onEvent(TransferEvent.UpdateSellInput(sellValue))
                        }
                    }

                    is TransferEffect.UpdateSellInput -> {
                        binding.etSell.removeTextChangedListener(sellTextWatcher)

                        val formattedSell = String.format("%.2f", effect.sell)
                        binding.etSell.setText(formattedSell)

                        sellTextWatcher = binding.etSell.addTextChangedListener { editable ->
                            val sellText = editable?.toString().orEmpty()
                            val sellValue = sellText.toDoubleOrNull() ?: 0.0
                            val buyValue = sellValue / currency
                            viewModel.onEvent(TransferEvent.UpdateBuyInput(buyValue))
                        }
                    }
                }
            }
        }
    }

    private fun navigateToChooseCard() {
        setUpFragmentResultForChooseCard()
        val direction = TransferFragmentDirections.actionTransferFragmentToChooseCardFragment()
        findNavController().navigate(direction)
    }

    private fun setUpFragmentResultForChooseCard() {
        parentFragmentManager.setFragmentResultListener(CARD_DETAILS_KEY, this) { _, bundle ->
            val selectedOption = bundle.getParcelable(SELECTED_OPTION_KEY, CardUi::class.java)
            viewModel.onEvent(TransferEvent.CardSelected(selectedOption))
        }
    }

    private fun navigateToChooseDestination() {
        setUpFragmentResultForChooseDestination()
        val direction =
            TransferFragmentDirections.actionTransferFragmentToChooseDestinationFragment()
        findNavController().navigate(direction)
    }

    private fun setUpFragmentResultForChooseDestination() {
        parentFragmentManager.setFragmentResultListener(
            DESTINATION_CARD_DETAILS_KEY,
            this
        ) { _, bundle ->
            val selectedOption = bundle.getParcelable(SELECTED_OPTION_KEY, CardUi::class.java)
            viewModel.onEvent(TransferEvent.ToCardSelected(selectedOption))
        }
    }

    // enable description and sell inputs only
    private fun enableSellAndDescriptionInputs(currency: CardUi.Currency?) {
        with(binding) {
            buyContainer.hide()
            sellContainer.show()
            descriptionContainer.show()
            tvSell.setText(R.string.amount)
            tvSellCurrency.setText(
                when (currency) {
                    CardUi.Currency.EUR -> R.string.EUR
                    CardUi.Currency.GEL -> R.string.GEL
                    CardUi.Currency.USD -> R.string.USD
                    null -> R.string.USD
                }
            )
        }
    }

    private fun enableAllFields(fromCurrency: CardUi.Currency?, toCurrency: CardUi.Currency?) {
        with(binding) {
            sellContainer.show()
            tvSell.setText(R.string.sell)
            buyContainer.show()
            descriptionContainer.show()

            tvSellCurrency.setText(
                when (fromCurrency) {
                    CardUi.Currency.EUR -> R.string.EUR
                    CardUi.Currency.GEL -> R.string.GEL
                    CardUi.Currency.USD -> R.string.USD
                    null -> R.string.USD
                }
            )

            tvBuyCurrency.setText(
                when (toCurrency) {
                    CardUi.Currency.EUR -> R.string.EUR
                    CardUi.Currency.GEL -> R.string.GEL
                    CardUi.Currency.USD -> R.string.USD
                    null -> R.string.USD
                }
            )
        }
    }

    private fun setUpSellChangedListener() {
        sellTextWatcher = binding.etSell.addTextChangedListener { editable ->
            val sellText = editable?.toString().orEmpty()
            val sellValue = sellText.toDoubleOrNull() ?: 0.0
            val buyValue = sellValue * currency
            viewModel.onEvent(TransferEvent.UpdateBuyInput(buyValue))
        }
    }

    private fun setUpBuyChangedListener() {
        buyTextWatcher = binding.etBuy.addTextChangedListener { editable ->
            val buyText = editable?.toString().orEmpty()
            val buyValue = buyText.toDoubleOrNull() ?: 0.0
            val sellValue = buyValue / currency
            viewModel.onEvent(TransferEvent.UpdateSellInput(sellValue))
        }
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

    private fun setUpFromCardUi(card: CardUi?) {
        with(binding) {
            card?.let {
                tvFromCardName.text = card.accountName
                val lastFourDigits = card.accountNumber?.takeLast(4)
                tvFromCardNumber.text = getString(R.string.account_number, lastFourDigits)
                tvFromAmount.text = card.balance.toString()
                imgFromCard.setImageResource(
                    when (card.cardType) {
                        CardUi.CardType.MASTER_CARD -> R.drawable.mastercard
                        CardUi.CardType.VISA -> R.drawable.visa
                        else -> R.drawable.visa
                    }
                )
                tvFromCurrency.text = handleCurrency(card.valuteType)
            }
        }
    }

    private fun setUpToCardUi(card: CardUi?) {
        with(binding) {
            card?.let {
                tvToCardName.text = card.accountName
                val lastFourDigits = card.accountNumber?.takeLast(4)
                tvToCardNumber.text = getString(R.string.account_number, lastFourDigits)
                tvToAmount.text = card.balance.toString()
                imgToCard.setImageResource(
                    when (card.cardType) {
                        CardUi.CardType.MASTER_CARD -> R.drawable.mastercard
                        CardUi.CardType.VISA -> R.drawable.visa
                        else -> R.drawable.visa
                    }
                )
                tvToCurrency.text = handleCurrency(card.valuteType)
            }
        }
    }

    private fun handleCurrency(currency: CardUi.Currency?): String {
        return when (currency) {
            CardUi.Currency.EUR -> getString(R.string.EUR)
            CardUi.Currency.GEL -> getString(R.string.GEL)
            CardUi.Currency.USD -> getString(R.string.USD)
            null -> getString(R.string.USD)
        }
    }

    companion object {
        const val CARD_DETAILS_KEY = "card_details"
        const val DESTINATION_CARD_DETAILS_KEY = "destination_card_details"
        const val SELECTED_OPTION_KEY = "selected_option"
    }
}