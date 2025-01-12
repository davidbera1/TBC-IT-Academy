package com.example.learnandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.learnandroid.R
import com.example.learnandroid.adapters.ViewPagerAdapter
import com.example.learnandroid.data.OrderData
import com.example.learnandroid.databinding.FragmentHolderBinding
import com.example.learnandroid.model.OrderItem
import com.google.android.material.tabs.TabLayoutMediator

class HolderFragment : Fragment() {

    private var _binding: FragmentHolderBinding? = null
    private val binding get() = _binding!!
    private lateinit var orders: MutableList<OrderItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHolderBinding.inflate(inflater, container, false)

        // get order list from object
        orders = OrderData.getOrders()

        // fill list only once, when it's empty initially
        if (orders.isEmpty()) {
            setUpOrderItems()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewPager()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpViewPager() {
        val adapter = ViewPagerAdapter(this)
        binding.viewPager2.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when(position) {
                0 -> tab.text = "Active"
                1 -> tab.text = "Completed"
            }
        }.attach()
    }

    private fun setUpOrderItems() {
        val orders = mutableListOf(
            OrderItem(
                name = "Modern Wingback",
                color = "Black",
                status = "Completed",
                quantity = 2,
                price = 280.00,
                image = R.drawable.modern_wingback
            ),
            OrderItem(
                name = "Wooden Chair",
                color = "Brown",
                status = "Completed",
                quantity = 3,
                price = 140.00,
                image = R.drawable.wooden_chair
            ),
            OrderItem(
                name = "Mirrored Reflector",
                color = "Black",
                status = "Completed",
                quantity = 1,
                price = 90.00,
                image = R.drawable.mirrored_reflector
            ),
            OrderItem(
                name = "Mini Bookshelf",
                color = "Brown",
                status = "Completed",
                quantity = 1,
                price = 110.00,
                image = R.drawable.mini_bookshelf
            ),
            OrderItem(
                name = "Lawson Chair",
                color = "Blue Grey",
                status = "Active",
                quantity = 1,
                price = 500.00,
                image = R.drawable.lawson_chair
            ),
            OrderItem(
                name = "Parabolic Reflector",
                color = "Brown",
                status = "Active",
                quantity = 3,
                price = 170.00,
                image = R.drawable.parabolic_reflector
            ),
            OrderItem(
                name = "Mini Wooden Table",
                color = "Brown",
                status = "Active",
                quantity = 3,
                price = 165.00,
                image = R.drawable.mini_wooden_table
            ),
            OrderItem(
                name = "Wooden Wardobe",
                color = "Brown",
                status = "Active",
                quantity = 1,
                price = 300.00,
                image = R.drawable.wooden_wardobe
            )
        )

        OrderData.addOrders(orders)
    }
}