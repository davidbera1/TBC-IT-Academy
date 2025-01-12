package com.example.learnandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnandroid.adapters.OrderAdapter
import com.example.learnandroid.data.OrderData
import com.example.learnandroid.databinding.FragmentCompletedOrdersBinding
import com.example.learnandroid.model.OrderItem

class CompletedOrdersFragment : Fragment() {

    private lateinit var orderAdapter: OrderAdapter
    private lateinit var orders: MutableList<OrderItem>

    private var _binding: FragmentCompletedOrdersBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompletedOrdersBinding.inflate(inflater, container, false)

        orders = OrderData.getOrders()
        val completedOrders = filterCompletedOrders(orders)

        orderAdapter = OrderAdapter { order ->
            val direction = HolderFragmentDirections.actionHolderFragmentToReviewBottomSheetDialogFragment(order)
            findNavController().navigate(direction)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
        }
        orderAdapter.submitList(completedOrders)

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun filterCompletedOrders(orders: MutableList<OrderItem>) : MutableList<OrderItem> {
        return orders.filter { it.status == "Completed"}.toMutableList()
    }
}
