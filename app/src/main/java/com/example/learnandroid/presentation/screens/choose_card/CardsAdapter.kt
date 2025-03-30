package com.example.learnandroid.presentation.screens.choose_card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.R
import com.example.learnandroid.databinding.CardItemBinding
import com.example.learnandroid.presentation.model.CardUi

class CardsAdapter(val onItemClicked: (CardUi) -> Unit) :
    ListAdapter<CardUi, CardsAdapter.CardsViewHolder>(CardsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        val card = getItem(position)
        holder.onBind(card)
    }


    inner class CardsViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(card: CardUi) {
            with(binding) {
                overlay.setOnClickListener {
                    onItemClicked(card)
                }
                tvCardName.text = card.accountName
                tvBalance.text = card.balance.toString()
                tvCurrency.setText(
                    when (card.valuteType) {
                        CardUi.Currency.EUR -> R.string.EUR
                        CardUi.Currency.GEL -> R.string.GEL
                        CardUi.Currency.USD -> R.string.USD
                        null -> R.string.USD
                    }
                )
                imgCard.setImageResource(
                    when (card.cardType) {
                        CardUi.CardType.MASTER_CARD -> R.drawable.mastercard
                        CardUi.CardType.VISA -> R.drawable.visa
                        null -> R.drawable.visa
                    }
                )
                val lastFourDigits = card.accountNumber?.takeLast(4)
                tvCardNumber.text =
                    itemView.context.getString(R.string.account_number, lastFourDigits)
            }
        }
    }
}

class CardsDiffUtil : DiffUtil.ItemCallback<CardUi>() {
    override fun areItemsTheSame(oldItem: CardUi, newItem: CardUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CardUi, newItem: CardUi): Boolean {
        return oldItem == newItem
    }
}