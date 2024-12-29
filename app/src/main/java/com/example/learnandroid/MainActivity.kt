package com.example.learnandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.learnandroid.data.OrderItems
import com.example.learnandroid.data.OrderList
import com.example.learnandroid.data.StatusButtons
import com.example.learnandroid.data.StatusButtonsList
import com.example.learnandroid.databinding.ActivityMainBinding
import com.example.learnandroid.fragments.DetailsFragment
import com.example.learnandroid.fragments.MyOrdersFragment
import com.example.learnandroid.fragments.NavBarFragment

/** MainActivity:
 * this is the host for all fragments. Has two containers, one for NavBar and second for all other fragments.
 * Sets up initial status buttons and orders and updates them in StatusButtonsList and OrderList objects.
 * Handles lambda callback from MyOrdersFragment and has startDetailFragment function which starts DetailsFragment and passes orderId to it.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
    }

    private fun setUp() {
        // set up status buttons list and save them in StatusButtonsList object
        val statusButtonsList = mutableListOf(
            StatusButtons(1, getString(R.string.pending)),
            StatusButtons(2, getString(R.string.delivered)),
            StatusButtons(3, getString(R.string.canceled))
        )
        StatusButtonsList.statusButtonsList.addAll(statusButtonsList)

        // set up order list and save them in OrderList object
        val orderItemsList = mutableListOf(
            OrderItems(
                orderId = 1524,
                trackingNumber = "IK287368838",
                quantity = 2,
                date = 1620865320000,
                subtotal = "$110"
            ),
            OrderItems(
                orderId = 1525,
                trackingNumber = "IK2873218897",
                quantity = 3,
                date = 1620778920000,
                subtotal = "$230"
            ),
            OrderItems(
                orderId = 1526,
                trackingNumber = "IK2873218645",
                quantity = 5,
                date = 1620606120000,
                subtotal = "$490"
            ),
            OrderItems(
                orderId = 1527,
                trackingNumber = "IK287363345",
                quantity = 2,
                date = 1620865320000,
                subtotal = "$170"
            ),
            OrderItems(
                orderId = 1528,
                trackingNumber = "IK2873457301",
                quantity = 22,
                date = 1620778920000,
                subtotal = "$830"
            ),
            OrderItems(
                orderId = 1529,
                trackingNumber = "IK2846457799",
                quantity = 10,
                date = 1620606120000,
                subtotal = "$390"
            )
        )
        OrderList.orderList.addAll(orderItemsList)

        // start MyOrdersFragment and handle lambda callback
        val myOrdersFragment = MyOrdersFragment().apply {
            navigateToDetailsFragment = { orderId ->
                startDetailFragment(orderId)
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, myOrdersFragment)
            .commit()


        // start NavBarFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.navBarFragmentContainer, NavBarFragment())
            .commit()
    }

    // function to start DetailsFragment and handle lambda callback from MyOrdersFragment
    private fun startDetailFragment(orderId: Int) {
        val detailsFragment = DetailsFragment.newInstance(orderId)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, detailsFragment)
            .addToBackStack(null)
            .commit()
    }
}
