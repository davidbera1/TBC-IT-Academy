package com.example.learnandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.learnandroid.data.OrderList
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentDetailsBinding
import java.text.SimpleDateFormat
import java.util.Date

/** DetailsFragment:
 * this fragment receives orderId when created, accesses orderList from OrderList object and shows the details accordingly about selected order.
 * Allows user to change status from pending to delivered or canceled only once. After status gets updated, toast message is shown and buttons get removed.
 * */

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private var orderId: Int? = null
    private var orderList = OrderList.orderList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderId = arguments?.getInt("orderId")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()
    }

    private fun setUp() {
        // find the order which was selected using orderId in orderList
        val currentOrder = orderList.find { it.orderId == orderId }

        // set up view texts
        with(binding) {
            tvOrderId.text = "${ContextCompat.getString(root.context, R.string.order)}${currentOrder?.orderId}"
            tvTrackingNumber.text = currentOrder?.trackingNumber
            tvQuantity.text = currentOrder?.quantity.toString()
            tvSubtotal.text = currentOrder?.subtotal
            tvDate.text = currentOrder?.date?.let { formatDate(it) }
            tvStatus.text = currentOrder?.status
        }

        // set up listener, update status, status color, show message and hide buttons
        binding.btnUpdateStatusToDelivered.setOnClickListener {
            currentOrder?.status = "delivered"
            binding.tvStatus.text = currentOrder?.status
            updateStatusTextColor()
            hideButtonsAndInfo()
            Toast.makeText(context, getString(R.string.status_updated_to_delivered), Toast.LENGTH_SHORT).show()
        }

        // set up listener, update status, status color, show message and hide buttons
        binding.btnUpdateStatusToCanceled.setOnClickListener {
            currentOrder?.status = "canceled"
            binding.tvStatus.text = currentOrder?.status
            updateStatusTextColor()
            hideButtonsAndInfo()
            Toast.makeText(context, getString(R.string.status_updated_to_canceled), Toast.LENGTH_SHORT).show()
        }

        // if status is not pending, hide the buttons and info text
        if (binding.tvStatus.text != "pending") {
            hideButtonsAndInfo()
        }
        // set initial text color
        updateStatusTextColor()
    }

    private fun formatDate(date: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val dateLong = Date(date)
        return dateFormat.format(dateLong)
    }

    private fun hideButtonsAndInfo() {
        with(binding) {
            tvInfo.visibility = View.GONE
            btnUpdateStatusToDelivered.visibility = View.GONE
            btnUpdateStatusToCanceled.visibility = View.GONE
        }
    }

    private fun updateStatusTextColor() {
        when(binding.tvStatus.text) {
            "pending" -> binding.tvStatus.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.pending
            ))
            "delivered" -> binding.tvStatus.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.delivered
            ))
            "canceled" -> binding.tvStatus.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.canceled
            ))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(orderId: Int) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt("orderId", orderId)
                }
            }
    }
}