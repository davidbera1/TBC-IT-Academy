package com.example.learnandroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.data.OrderItems
import com.example.learnandroid.data.OrderList
import com.example.learnandroid.R
import com.example.learnandroid.databinding.OrderItemBinding
import java.text.SimpleDateFormat
import java.util.Date

class OrderItemsDiffUtil: DiffUtil.ItemCallback<OrderItems>() {
    override fun areItemsTheSame(oldItem: OrderItems, newItem: OrderItems): Boolean {
        return oldItem.orderId == newItem.orderId
    }

    override fun areContentsTheSame(oldItem: OrderItems, newItem: OrderItems): Boolean {
        return oldItem == newItem
    }
}

class OrderItemsAdapter : ListAdapter<OrderItems, OrderItemsAdapter.ViewHolder>(OrderItemsDiffUtil()){

    var onDetailButtonClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // when creating orders, show pending orders by default
        filterOrders("pending")
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(orderItems: OrderItems) {
            // set up texts
            with(binding) {
                tvOrderId.text = "${ContextCompat.getString(root.context, R.string.order)}${orderItems.orderId}"
                tvTrackingNumber.text = orderItems.trackingNumber
                tvQuantity.text = orderItems.quantity.toString()
                tvStatus.text = orderItems.status
                tvSubtotal.text = orderItems.subtotal
                tvDate.text = formatDate(orderItems.date)
            }
            // set up status color
            if (orderItems.status == "pending") {
                binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context,
                    R.color.pending
                ))
            }
            else if(orderItems.status == "delivered") {
                binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context,
                    R.color.delivered
                ))
            }
            else if (orderItems.status == "canceled") {
                binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context,
                    R.color.canceled
                ))
            }

            // set onClickListener for details button
            binding.btnDetails.setOnClickListener {
                onDetailButtonClick?.invoke(orderItems.orderId)
            }
        }
    }

    private fun formatDate(date: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val dateLong = Date(date)
        return dateFormat.format(dateLong)
    }

    fun filterOrders(status: String) {
        val filteredList = OrderList.orderList.filter { orderItems ->
            orderItems.status == status
        }
        submitList(filteredList)
    }
}