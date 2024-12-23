package com.example.learnandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.databinding.RecyclerviewHorizontalBinding


class HorizontalRecyclerViewAdapter(
    private val items: List<HorizontalRecylerViewItem>,
    private val onCategorySelected: (String) -> Unit
) : RecyclerView.Adapter<HorizontalRecyclerViewAdapter.ViewHolder>() {

    private var selectedPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HorizontalRecyclerViewAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.button.text = item.buttonText

        updateButtonColors(holder, position)

        holder.binding.button.setOnClickListener {
            handleButtonClick(position)
            onCategorySelected(item.buttonText)
        }
    }

    private fun updateButtonColors(holder: ViewHolder, position: Int) {
        if (position == selectedPosition) {
            holder.binding.button.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.button_background_green)
            holder.binding.button.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        } else {
            holder.binding.button.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.button_background_default)
            holder.binding.button.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.text_color))
        }
    }

    private fun handleButtonClick(position: Int) {
        if (selectedPosition != position) {
            val previousPosition = selectedPosition
            selectedPosition = position

            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }
    }

    inner class ViewHolder(val binding: RecyclerviewHorizontalBinding) : RecyclerView.ViewHolder(binding.root)
}