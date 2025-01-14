package com.example.learnandroid.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentAddCardBinding
import com.example.learnandroid.model.CardDetails
import com.example.learnandroid.model.CardType
import java.util.UUID

class AddCardFragment : BaseFragment<FragmentAddCardBinding>(FragmentAddCardBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
    }

    private fun setUpListeners() {
        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        radioButtonsListener()

        expiresInputListener()

        addCardListener()
    }

    private fun radioButtonsListener() {
        with(binding) {
            rbMastercard.setOnClickListener {
                imgCard.setImageResource(R.drawable.mastercard)
                rbMastercard.isChecked = true
                rbVisa.isChecked = false
            }

            rbVisa.setOnClickListener {
                imgCard.setImageResource(R.drawable.visa)
                rbVisa.isChecked = true
                rbMastercard.isChecked = false
            }
        }
    }

    private fun validateName(name: String): Boolean {
        if (name.isEmpty()) {
            return false
        }

        val allowedSymbols = getString(R.string.allowed_symbols_for_name)
        name.forEach { char ->
            if (char !in allowedSymbols) {
                return false
            }
        }

        return true
    }

    private fun validateExpires(expires: String): Boolean {

        if (expires.isEmpty() || expires.length != 5) return false

        if (expires[0].isDigit() && expires[1].isDigit() && expires[2] == '/' && expires[3].isDigit() && expires[4].isDigit()) {
            return true
        }

        return false
    }

    private fun expiresInputListener() {
        with(binding) {
            etExpires.doAfterTextChanged {
                val input = etExpires.text.toString()
                if (input.length == 2 && input[0].isDigit() && input[1].isDigit()) {
                    etExpires.setText("${etExpires.text}/")
                    // without this, cursor goes at the start after adding /
                    etExpires.setSelection(3)
                }
            }
        }
    }

    private fun addCardListener() {
        with(binding) {
            btnAddCard.setOnClickListener {
                val cardHolderName = etCardHolderName.text.toString()
                val cardNumber = etCardNumber.text.toString()
                val expires = etExpires.text.toString()
                val cvv = etCVV.text.toString()

                val cardType = if (rbMastercard.isChecked) {
                    CardType.Mastercard
                } else {
                    CardType.Visa
                }

                if (validateName(cardHolderName) && validateExpires(expires) && cvv.length == 3 && cardNumber.length == 16) {
                    val image = if (cardType == CardType.Mastercard) {
                        R.drawable.mastercard
                    } else {
                        R.drawable.visa
                    }

                    val cardDetails = CardDetails(
                        id = UUID.randomUUID().toString(),
                        image = image,
                        cardHolderName = cardHolderName,
                        cardNumber = cardNumber,
                        expires = expires,
                        cvv = cvv.toInt(),
                        cardType = cardType
                    )

                    Toast.makeText(requireContext(), getString(R.string.card_was_added_successfully), Toast.LENGTH_SHORT).show()
                    val direction = AddCardFragmentDirections.actionAddCardFragmentToPaymentFragment(cardDetails = cardDetails)
                    findNavController().navigate(direction)
                } else {
                    Toast.makeText(requireContext(), getString(R.string.please_fill_all_fields_correctly), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}