package com.example.learnandroid.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.R
import com.example.learnandroid.data.StatusButtons
import com.example.learnandroid.databinding.StatusItemBinding

class StatusButtonsDiffUtil: DiffUtil.ItemCallback<StatusButtons>() {

    override fun areItemsTheSame(oldItem: StatusButtons, newItem: StatusButtons): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: StatusButtons, newItem: StatusButtons): Boolean {
        return oldItem.text == newItem.text
    }
}

class StatusButtonsAdapter : ListAdapter<StatusButtons, StatusButtonsAdapter.ViewHolder>(StatusButtonsDiffUtil()) {

    private var currentSelected: Int = 0
    var filterOrders: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StatusItemBinding.inflate(LayoutInflater.from((parent.context)), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    inner class ViewHolder(private val binding: StatusItemBinding) : RecyclerView.ViewHolder (binding.root) {
        fun bind(status: StatusButtons) {
            binding.btnStatus.text = status.text

            // if button is selected, change the background and text color
            if (currentSelected==adapterPosition) {
                with(binding) {
                    btnStatus.setBackgroundResource(R.drawable.button_selected_background)
                    btnStatus.setTextColor(ContextCompat.getColor(root.context, R.color.white))
                }
            }
            else {
                with(binding) {
                    btnStatus.setBackgroundColor(Color.TRANSPARENT)
                    btnStatus.setTextColor(ContextCompat.getColor(root.context, R.color.black))
                }
            }

            binding.btnStatus.setOnClickListener {
                // invoke callback lambda and pass filter name
                when(adapterPosition) {
                    0 -> filterOrders?.invoke("pending")
                    1 -> filterOrders?.invoke("delivered")
                    2 -> filterOrders?.invoke("canceled")
                }

                val previousSelected = currentSelected
                currentSelected = adapterPosition
                notifyItemChanged(previousSelected)
                notifyItemChanged(currentSelected)
            }
        }
    }
}