package com.example.learnandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnandroid.adapters.OrderAdapter
import com.example.learnandroid.data.OrderData
import com.example.learnandroid.databinding.FragmentActiveOrdersBinding
import com.example.learnandroid.model.OrderItem

class ActiveOrdersFragment : Fragment() {

    private lateinit var orderAdapter: OrderAdapter
    private lateinit var orders: MutableList<OrderItem>

    private var _binding: FragmentActiveOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActiveOrdersBinding.inflate(inflater, container, false)

        orders = OrderData.getOrders()
        val activeOrders = filterActiveOrders(orders)

        orderAdapter = OrderAdapter { }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
        }
        orderAdapter.submitList(activeOrders)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun filterActiveOrders(orders: MutableList<OrderItem>) : MutableList<OrderItem> {
        return orders.filter { it.status == "Active"}.toMutableList()
    }
}