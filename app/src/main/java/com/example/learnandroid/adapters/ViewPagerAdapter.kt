package com.example.learnandroid.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.learnandroid.fragments.ActiveOrdersFragment
import com.example.learnandroid.fragments.CompletedOrdersFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ActiveOrdersFragment()
            1 -> CompletedOrdersFragment()
            else -> throw IllegalStateException("Invalid position")
        }
    }
}
