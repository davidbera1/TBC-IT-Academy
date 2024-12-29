package com.example.learnandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnandroid.adapters.OrderItemsAdapter
import com.example.learnandroid.data.OrderList
import com.example.learnandroid.adapters.StatusButtonsAdapter
import com.example.learnandroid.data.StatusButtonsList
import com.example.learnandroid.databinding.FragmentMyOrdersBinding

/** MyOrdersFragment:
 *  this is the main fragment that shows the list of orders vertically and the status buttons horizontally by using 2 RecyclerViews.
 *  The OrderItemsAdapter handles the list of orders and triggers onDetailButtonClick callback lambda when user clicks details button.
 *  This fragment has a lambda callback navigateToDetailsFragment for MainActivity, passing orderId to it which came from OrderItemsAdapter.
 *  StatusButtonsAdapter handles the list of status buttons and triggers filterOrders lambda callback and passes filter name when user clicks on status buttons.
 *  Lambda callback is handled by this fragment and passed to OrderItemsAdapter to execute filterOrders function.
 *  */

class MyOrdersFragment : Fragment() {

    private var _binding: FragmentMyOrdersBinding? = null
    private val binding get() = _binding!!
    private var statusButtonsList = StatusButtonsList.statusButtonsList
    private var orderList = OrderList.orderList

    // callback lambda for MainActivity, passing orderId to DetailsFragment
    var navigateToDetailsFragment: ((Int) -> Unit)? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerViews() {
        // set up order items RecyclerView
        val rvOrder = binding.rvOrders
        val orderItemsAdapter = OrderItemsAdapter().apply {
            // handle lambda callback from OrderItemsAdapter and pass orderId to MainActivity
            onDetailButtonClick = { orderId ->
                navigateToDetailsFragment?.invoke(orderId)
            }
        }
        rvOrder.layoutManager = LinearLayoutManager(requireContext())
        rvOrder.adapter = orderItemsAdapter
        orderItemsAdapter.submitList(orderList)

        // set up status buttons RecyclerView and handle lambda callback by passing filter name to OrderItemsAdapter
        val statusButtonsAdapter = StatusButtonsAdapter().apply {
            filterOrders = { status ->
                orderItemsAdapter.filterOrders(status)
            }
        }
        val rvStatusButtons = binding.rvStatusButtons
        rvStatusButtons.adapter = statusButtonsAdapter
        rvStatusButtons.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        statusButtonsAdapter.submitList(statusButtonsList)
    }

}