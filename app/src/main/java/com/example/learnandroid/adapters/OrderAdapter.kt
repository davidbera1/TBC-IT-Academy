package com.example.learnandroid.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.R
import com.example.learnandroid.databinding.OrderItemBinding
import com.example.learnandroid.model.OrderItem

class OrderAdapter(private val onItemClick: (OrderItem) -> Unit) : ListAdapter<OrderItem, OrderAdapter.OrderViewHolder>(OrderDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        holder.onBind(order)
    }

    inner class OrderViewHolder(private val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("DefaultLocale", "SetTextI18n")
        fun onBind(orderItem: OrderItem) {

            with(binding) {
                tvName.text = orderItem.name
                imgPhoto.setImageResource(orderItem.image)
                tvColor.text = orderItem.color
                tvQuantity.text = "${binding.tvQuantity.text}${orderItem.quantity}"
                btnStatus.text = orderItem.status
                tvPrice.text = String.format("%.2f", orderItem.price)
            }

            // set status texts
            when(orderItem.status) {
                "Completed" -> binding.btnStatus.text = orderItem.status
                "Active" -> binding.btnStatus.text = "In Delivery"
            }

            // set circle colors according to orderItem color
            when(orderItem.color.lowercase()) {
                "black" -> binding.imgColor.setImageResource(R.drawable.circle_black)
                "brown" -> binding.imgColor.setImageResource(R.drawable.circle_brown)
                "blue grey" -> binding.imgColor.setImageResource(R.drawable.circle_blue_grey)
            }

            // set action button texts according to status
            if(orderItem.status == "Completed") {
                binding.btnAction.text = "Leave Review"
                binding.btnAction.setOnClickListener { onItemClick(orderItem) }
            }
            else {
                binding.btnAction.text = "Track Order"
            }
        }
    }

    class OrderDiffUtil : DiffUtil.ItemCallback<OrderItem>() {
        override fun areItemsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
            return oldItem == newItem
        }
    }
}
