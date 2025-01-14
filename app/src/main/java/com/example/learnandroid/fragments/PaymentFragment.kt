package com.example.learnandroid.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.learnandroid.adapter.CardAdapter
import com.example.learnandroid.databinding.FragmentPaymentBinding
import com.example.learnandroid.model.CardDetails
import com.example.learnandroid.model.PaymentViewModel

class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate) {

    private val args: PaymentFragmentArgs by navArgs()
    private val viewModel: PaymentViewModel by activityViewModels()

    private lateinit var cardList: List<CardDetails>
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: CardAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addNewCard()
        setUpListeners()
        setUpViewPager2()
    }

    private fun setUpListeners() {
        binding.tvAddNew.setOnClickListener {
            val direction = PaymentFragmentDirections.actionPaymentFragmentToAddCardFragment()
            findNavController().navigate(direction)
        }
    }

    private fun setUpViewPager2() {
        cardList = viewModel.getCardList()
        viewPager2 = binding.viewPager2

        adapter = CardAdapter(onItemLongClick = { id ->
            showDeleteConfirmationDialog(id)
        })

        viewPager2.adapter = adapter
        adapter.submitList(cardList)
    }

    private fun addNewCard() {
        args.cardDetails?.let { card ->
            viewModel.addCard(card)
        }
    }

    private fun showDeleteConfirmationDialog(id: String) {
        val deleteConfirmationDialog = DeleteConfirmationDialog(onYesButtonClicked = {
            viewModel.deleteCard(id=id)
            // without reassigning adapter, UI doesn't get updated immediately
            val updatedList = viewModel.getCardList()
            viewPager2.adapter = adapter
            adapter.submitList(updatedList)
        })

        deleteConfirmationDialog.show(parentFragmentManager, null)
    }
}