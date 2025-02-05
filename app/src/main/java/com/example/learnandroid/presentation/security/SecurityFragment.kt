package com.example.learnandroid.presentation.security

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentSecurityBinding
import com.example.learnandroid.presentation.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SecurityFragment : BaseFragment<FragmentSecurityBinding>(FragmentSecurityBinding::inflate) {

    private val viewModel: SecurityViewModel by viewModels()

    override fun start() {
        observe()
    }

    override fun setUpListeners() {
        setNumpadListeners()
        binding.btnBackspace.setOnClickListener { viewModel.removePasswordLastDigit() }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.enteredPassword.collectLatest { enteredPassword ->
                if (enteredPassword.length == viewModel.correctPassword.length) {
                    if (viewModel.checkPassword(enteredPassword)) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.success),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.incorrect_passcode),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                handleCircles(enteredPassword.length)
            }
        }
    }

    private fun handleCircles(numberOfCirclesOn: Int) {
        with(binding) {
            val circles = listOf(imgCircle1, imgCircle2, imgCircle3, imgCircle4)

            circles.forEachIndexed { index, imgCircle ->
                if (index + 1 <= numberOfCirclesOn) {
                    imgCircle.setImageResource(R.drawable.circle_green)
                } else {
                    imgCircle.setImageResource(R.drawable.circle_gray)
                }
            }
        }
    }

    private fun setNumpadListeners() {
        with(binding) {
            btn0.setOnClickListener { viewModel.insertPasswordDigit('0') }
            btn1.setOnClickListener { viewModel.insertPasswordDigit('1') }
            btn2.setOnClickListener { viewModel.insertPasswordDigit('2') }
            btn3.setOnClickListener { viewModel.insertPasswordDigit('3') }
            btn4.setOnClickListener { viewModel.insertPasswordDigit('4') }
            btn5.setOnClickListener { viewModel.insertPasswordDigit('5') }
            btn6.setOnClickListener { viewModel.insertPasswordDigit('6') }
            btn7.setOnClickListener { viewModel.insertPasswordDigit('7') }
            btn8.setOnClickListener { viewModel.insertPasswordDigit('8') }
            btn9.setOnClickListener { viewModel.insertPasswordDigit('9') }
        }
    }
}