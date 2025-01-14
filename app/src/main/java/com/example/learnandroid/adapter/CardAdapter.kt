package com.example.learnandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.databinding.CardItemBinding
import com.example.learnandroid.model.CardDetails

class CardAdapter(
    private val onItemLongClick: (String) -> Unit
) : ListAdapter<CardDetails, CardAdapter.CardViewHolder>(CardDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = getItem(position)
        holder.onBind(card)
    }

    inner class CardViewHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(cardDetails: CardDetails) {
            with(binding) {
                image.setImageResource(cardDetails.image)
                tvCardHolderName.text = cardDetails.cardHolderName
                tvExpires.text = cardDetails.expires
                tvCardNumber.text = buildCardNumber(cardDetails.cardNumber)

                root.setOnLongClickListener {
                    onItemLongClick(cardDetails.id)
                    true
                }
            }
        }
    }

    private fun buildCardNumber(number: String): String {
        var result = ""

        if (number.length != 16) {
            return result
        }

        val num1to4 = number.substring(0, 4)
        val num5to8 = number.substring(4, 8)
        val num9to12 = number.substring(8, 12)
        val num13to16 = number.substring(12, 16)

        result = "$num1to4     $num5to8     $num9to12     $num13to16"

        return result
    }
}

class CardDiffUtil : DiffUtil.ItemCallback<CardDetails>() {
    override fun areItemsTheSame(oldItem: CardDetails, newItem: CardDetails): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CardDetails, newItem: CardDetails): Boolean {
        return oldItem == newItem
    }
}